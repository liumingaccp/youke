package youke.service.wx.provider;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.TMassRecord;
import youke.common.model.TMassTask;
import youke.common.model.TMaterialFile;
import youke.common.model.TMaterialNews;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.provider.IWeixinMassService;
import youke.facade.wx.util.Constants;
import youke.facade.wx.util.MediaType;
import youke.facade.wx.vo.material.SysNewsTreeVo;
import youke.facade.wx.vo.material.SysnewsVo;
import youke.facade.wx.vo.message.Article;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.biz.IKefuMessageBiz;
import youke.service.wx.biz.IMassMessageBiz;
import youke.service.wx.biz.IMaterialBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/9
 * Time: 16:01
 *
 * 做微信的异步群发
 */
@Service
public class WeixinMassService implements IWeixinMassService{
    @Resource
    private IMassTaskDao massTaskDao;
    @Resource
    private IKefuMessageBiz kefuMessageBiz;
    @Resource
    private IMassMessageBiz massMessageBiz;
    @Resource
    private IMaterialBiz materialBiz;
    @Resource
    private IMaterialFileDao materialFileDao;
    @Resource
    private IMaterialNewsDao materialNewsDao;
    @Resource
    private IMaterialImageDao materialImageDao;
    @Resource
    private IMassRecordDao massRecordDao;
    @Resource
    private ICoreBiz coreBiz;
    @Resource
    private IMassTaskOpenIdDao massTaskOpenIdDao;
    @Resource
    private IMassFansDao massFansDao;
    private static Logger log = Logger.getLogger(WeixinMassService.class);

    public void mass(TMassTask task) {
        //1.群发任务记录状态修改
        task.setState(1);
        task.setSendType(task.getSendType());
        massTaskDao.updateByPrimaryKeySelective(task);
        String ret = null;
        List<String> openIds = massTaskOpenIdDao.selectByTaskId(task.getId(), task.getAppid());
        //2.如果互动群发群发,就采用客服发送接口
        if(task.getSendType() == 2){
            if(openIds != null && openIds.size() > 0){
                String mediaType = task.getMediatype();
                Integer materialid = task.getMaterialid();
                String appid = task.getAppid();
                KefuMassProcessThread thread = new KefuMassProcessThread(mediaType, materialid, appid, openIds, task.getId(), task.getContent());
                new Thread(thread).start();
            }
        }else if(task.getSendType() == 1 || task.getSendType() == 0){ //2.如果是普通类型和高级群发,就采用素材发送接口

            if(openIds != null && openIds.size() > 0){
                String mediaType = task.getMediatype();
                Integer materialid = task.getMaterialid();
                String appid = task.getAppid();

                StringBuffer massOpenIds = new StringBuffer();
                for(String openId : openIds) {
                    massOpenIds.append("\"" + openId + "\",");
                }
                massOpenIds.deleteCharAt(massOpenIds.length() - 1);
                try {
                    if(mediaType.equals(MediaType.TEXT)){
                        ret = massMessageBiz.sendText(appid, massOpenIds.toString(), task.getContent());

                    }else if(mediaType.equals(MediaType.IMG)){
                        String mediaId = materialImageDao.selectMediaIdById(materialid);
                        ret = massMessageBiz.sendImage(appid, massOpenIds.toString(), mediaId);
                    }else if(mediaType.equals(MediaType.VOICE)){
                        String mediaId = materialFileDao.selectMediaIdById(materialid);
                        ret = massMessageBiz.sendVoice(appid, massOpenIds.toString(), mediaId);
                    }else if(mediaType.equals(MediaType.VIDEO)){
                        TMaterialFile mater = materialFileDao.selectByPrimaryKey(materialid);
                        String jsonStr = massMessageBiz.getVideoMediaId(mater.getMediaid(), mater.getTitle(), mater.getIntroduction(), appid);
                        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
                        String media_id = jsonObject.getString("media_id");
                        ret =  massMessageBiz.sendVideo(appid, massOpenIds.toString(), media_id,null, mater.getTitle(), mater.getIntroduction());
                    }else if(mediaType.equals(MediaType.NUSIC)){
                        TMaterialFile materialFile = materialFileDao.selectByPrimaryKey(materialid);
                        //....微信不提供此接口
                    }else if(mediaType.equals(MediaType.NEWS)){
                        TMaterialNews tMaterialNews = materialNewsDao.selectByPrimaryKey(materialid);
                        ret = massMessageBiz.sendNews(appid, massOpenIds.toString(), tMaterialNews.getMediaid());
                    }
                }catch (BusinessException e){
                    log.error("微信群发--微信端错误"+e.getMessage());
                    TMassRecord record = massRecordDao.selectByTaskId(task.getId());
                    record.setState(2);
                    record.setFailRearon(e.getMessage());
                    record.setSuccessnum(0);
                    record.setOvertime(new Date());
                    record.setFailnum(openIds.size());
                    massRecordDao.updateByPrimaryKeySelective(record);

                    //修改群发的openIds的状态
                    massTaskOpenIdDao.updateStateByTaskId(task.getId(), task.getAppid(), 2);

                    //群发次数扣除
                    massFansDao.updateNumForOpendIds(openIds);

                }

                if(ret != null){
                    JSONObject jsonObject = JSONObject.fromObject(ret);
                    int msg_id = jsonObject.getInt("msg_id");
                    //修改
                    TMassRecord record = massRecordDao.selectByTaskId(task.getId());
                    if(record == null){
                        throw new BusinessException("发送任务不存在");
                    }
                    record.setState(1);
                    record.setSuccessnum(openIds.size());
                    record.setOvertime(new Date());
                    record.setFailnum(0);
                    massRecordDao.updateByPrimaryKeySelective(record);

                    MeriaMassProcessThread thread = new MeriaMassProcessThread(msg_id, appid, task.getId(), openIds);
                    new Thread(thread).start();
                }
            }

        }
    }

    //处理客服群发线程(控制群发速度)
    private class KefuMassProcessThread implements Runnable{

        String mediaType;
        Integer materialid;
        String appid;
        List<String> openIds;
        private int taskId;
        String messge;
        String content = "";

        String ret;
        int count;
        int failNum;

        KefuMassProcessThread(String mediaType, Integer materialid, String appid,List<String> openIds, int taskId, String content){
            this.mediaType = mediaType;
            this.materialid = materialid;
            this.appid = appid;
            this.openIds = openIds;
            this.taskId = taskId;
            this.count = 0;
            this.failNum = 0;
            this.content = content;
        }

        public void run() {
            if(mediaType.equals(MediaType.TEXT)){
                for(String openId : openIds) {
                    try {
                        ret = kefuMessageBiz.sendText(appid, openId, content);
                        WxCurlUtil.ret(ret);
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 1);
                       count+=1;
                    }catch (Exception e){
                        messge = e.getMessage();
                        failNum = failNum + 1;
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 2);
                    }
                    if(count % 50 == 0){
                        try {
                            Thread.sleep(3000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            Thread.sleep(300);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }else if(mediaType.equals(MediaType.IMG)){
                String mediaId = materialImageDao.selectMediaIdById(materialid);
                for(String openId : openIds) {
                    try {
                        ret = kefuMessageBiz.sendImage(appid, openId, mediaId);
                        WxCurlUtil.ret(ret);
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 1);
                        count+=1;
                    }catch (Exception e){
                        messge = e.getMessage();
                        failNum = failNum + 1;
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 2);
                    }
                    if(count % 50 == 0){
                        try {
                            Thread.sleep(3000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            Thread.sleep(300);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }else if(mediaType.equals(MediaType.VOICE)){
                String mediaId = materialFileDao.selectMediaIdById(materialid);
                for(String openId : openIds) {
                    try {
                        ret = kefuMessageBiz.sendVoice(appid, openId, mediaId);
                        WxCurlUtil.ret(ret);
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 1);
                        count+=1;
                    }catch (Exception e){
                        messge = e.getMessage();
                        failNum = failNum + 1;
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 2);
                    }
                    if(count % 50 == 0){
                        try {
                            Thread.sleep(3000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            Thread.sleep(300);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }else if(mediaType.equals(MediaType.VIDEO)){
                TMaterialFile mater = materialFileDao.selectByPrimaryKey(materialid);
                for(String openId : openIds) {
                    String jsonStr = kefuMessageBiz.getVideoMediaId(mater.getMediaid(), mater.getTitle(), mater.getIntroduction(), appid);
                    JSONObject jsonObject = JSONObject.fromObject(jsonStr);
                    String media_id = jsonObject.getString("media_id");
                    try {
                        ret =  kefuMessageBiz.sendVideo(appid, openId, mater.getMediaid() ,media_id, mater.getTitle(), mater.getIntroduction());
                        WxCurlUtil.ret(ret);
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 1);
                        count+=1;
                    }catch (Exception e){
                        messge = e.getMessage();
                        failNum = failNum + 1;
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 2);
                    }
                    if(count % 50 == 0){
                        try {
                            Thread.sleep(3000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            Thread.sleep(300);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }else if(mediaType.equals(MediaType.NUSIC)){
                TMaterialFile materialFile = materialFileDao.selectByPrimaryKey(materialid);
                //....微信不提供此接口
                for(String openId : openIds) {
                }
            }else if(mediaType.equals(MediaType.NEWS)){
                TMaterialNews tMaterialNews = materialNewsDao.selectByPrimaryKey(materialid);
                for(String openId : openIds) {
                    try {
                        ret = kefuMessageBiz.sendNews(appid, openId, tMaterialNews.getMediaid());
                        WxCurlUtil.ret(ret);
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 1);
                        count+=1;
                    }catch (Exception e){
                        messge = e.getMessage();
                        failNum = failNum + 1;
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 2);
                    }
                    if(count % 50 == 0){
                        try {
                            Thread.sleep(3000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            Thread.sleep(300);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }else if(mediaType.equals(MediaType.SYSNEWS)){
                SysNewsTreeVo treeVo = materialBiz.getSysnews(materialid, appid);
                List<Article> articleList = new ArrayList<Article>();
                articleList.add(new Article(treeVo.getNews().getTitle(),treeVo.getNews().getDescription(),treeVo.getNews().getPicUrl(),treeVo.getNews().getUrl()));
                if(treeVo.getSubNews() != null &&treeVo.getSubNews().size()>0)
                {
                    for (SysnewsVo newsVo:treeVo.getSubNews()) {
                        articleList.add(new Article(newsVo.getTitle(),newsVo.getDescription(),newsVo.getPicUrl(),newsVo.getUrl()));
                    }
                }
                for(String openId : openIds) {
                    try {
                        ret = kefuMessageBiz.sendNews(appid, openId, articleList);
                        WxCurlUtil.ret(ret);
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 1);
                        count+=1;
                    }catch (Exception e){
                        e.printStackTrace();
                        messge = e.getMessage();
                        failNum = failNum + 1;
                        massTaskOpenIdDao.updateByTaskAndOpendId(openId, taskId, 2);
                    }
                    if(count % 50 == 0){
                        try {
                            Thread.sleep(3000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            Thread.sleep(300);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

            }

            //修改
            TMassRecord record = massRecordDao.selectByTaskId(taskId);
            record.setState(1);
            record.setFailnum(0);
            record.setSuccessnum(count);
            record.setOvertime(new Date());
            record.setFailnum(failNum);
            record.setFailRearon(messge);
            massRecordDao.updateByPrimaryKeySelective(record);

            count = 0;
        }
    }

    //处理素材群发线程(监测结果)
    private class MeriaMassProcessThread implements Runnable{

        private Integer msg_id;

        private String appId;

        private int taskId;

        private List<String> openIds;

        private String url = Constants.BASEURL + "message/mass/get?access_token=ACCESS_TOKEN";
        //https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN
        private String status = null;

        public MeriaMassProcessThread(Integer mes_id, String appId, int taskId, List<String> openIds){
            this.msg_id = mes_id;
            this.appId = appId;
            this.taskId = taskId;
            if(openIds == null){
                this.openIds = new ArrayList<>();
            }
            else {
                this.openIds =openIds;
            }
        }

        public void run() {

            while (true){
                if (msg_id != null) {
                    url = url.replace("ACCESS_TOKEN", coreBiz.getToken(appId));
                    String msgIdJson = "{\"msg_id\": \"+"+ msg_id +"\"}";
                    String ret = HttpConnUtil.doPostJson(url, msgIdJson);
                    if(ret== null || !ret.startsWith("{")){
                        throw new BusinessException("微信服务端异常");
                    }
                    JSONObject jsonObject = JSONObject.fromObject(ret);
                    String status = jsonObject.has("msg_status") ? jsonObject.getString("msg_status") : null;
                    if("SENDING".equals(status)){
                        try {
                            Thread.sleep(1000 * 60);
                        }catch (InterruptedException e){

                        }

                    }else if("SEND_SUCCESS".equals(status)){
                        log.info("微信群发发送成功,taskId:"+taskId);
                        //修改
                        TMassRecord record = massRecordDao.selectByTaskId(taskId);
                        record.setState(1);
                        record.setFailnum(0);
                        record.setSuccessnum(openIds.size());
                        record.setOvertime(new Date());
                        record.setFailRearon("微信服务端异常");
                        massRecordDao.updateByPrimaryKeySelective(record);
                        //修改群发的openIds的状态
                        massTaskOpenIdDao.updateStateByTaskId(taskId, appId, 1);
                        break;
                    }else if("SEND_FAIL".equals(status)){
                        log.info("微信群发发送失败,taskId:"+taskId);
                        //修改
                        TMassRecord record = massRecordDao.selectByTaskId(taskId);
                        record.setState(2);
                        record.setFailnum(openIds.size());
                        record.setSuccessnum(0);
                        record.setOvertime(new Date());
                        record.setFailRearon("微信异常");
                        massRecordDao.updateByPrimaryKeySelective(record);
                        //修改群发的openIds的状态
                        massTaskOpenIdDao.updateStateByTaskId(taskId, appId, 2);
                        //群发次数扣除
                        massFansDao.updateNumForOpendIds(openIds);
                        break;
                    }else if("DELETE".equals(status)){
                        //修改
                        TMassRecord record = massRecordDao.selectByTaskId(taskId);
                        record.setState(3);
                        record.setFailnum(openIds.size());
                        record.setSuccessnum(0);
                        record.setOvertime(new Date());
                        massRecordDao.updateByPrimaryKeySelective(record);
                        //修改群发的openIds的状态
                        massTaskOpenIdDao.updateStateByTaskId(taskId, appId, 2);
                        //群发次数扣除
                        massFansDao.updateNumForOpendIds(openIds);
                        break;
                    }
                }
            }
        }
    }
}
