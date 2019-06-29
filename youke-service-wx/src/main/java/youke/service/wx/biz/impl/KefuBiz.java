package youke.service.wx.biz.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.dao.ISubscrConfigDao;
import youke.common.dao.ISubscrDao;
import youke.common.dao.ISubscrKefuDao;
import youke.common.exception.BusinessException;
import youke.common.exception.WxException;
import youke.common.model.TSubscr;
import youke.common.model.TSubscrKefu;
import youke.common.model.vo.file.SerializeMultipartFile;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.vo.kefu.KefuStateVo;
import youke.facade.wx.vo.kefu.KefuVo;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.biz.IKefuBiz;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2017/12/27
 * Time: 14:56
 */
@Service
public class KefuBiz implements IKefuBiz{

    @Resource
    private ICoreBiz coreBiz;
    @Resource
    private ISubscrKefuDao subscrKefuDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;

    private String kefu = "kf_online_list";

    public List<KefuVo> getKefus(String appId) {

        List<KefuVo> list = new ArrayList<KefuVo>();
        Map<String, TSubscrKefu> status = new HashMap<>();
        String ret = HttpConnUtil.doHttpRequest("https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=" + coreBiz.getToken(appId));
        this.ret(ret);
        JSONObject jsonObject = JSONObject.fromObject(ret);
        JSONArray kf_list = jsonObject.getJSONArray("kf_list");

        //查询本地数据,进行状态的修改
        List<TSubscrKefu> localList = subscrKefuDao.selectAll(appId);
        if(localList != null && localList.size() > 0){
            for(TSubscrKefu kefu : localList){
                status.put(kefu.getKefunum(), kefu);
            }
        }

        for(int i=0; i< kf_list.size(); i++){
            JSONObject object = kf_list.getJSONObject(i);
            //封装
            KefuVo kefuVo = new KefuVo(object);
            list.add(kefuVo);
            TSubscrKefu kefu = status.get(kefuVo.getAccount());
            if(kefu != null){
                if(status.get(kefuVo.getAccount()) != null){
                    kefuVo.setId(kefu.getId());
                    kefuVo.setFace(kefu.getFace());
                    if(kefu.getBindtime() != null){
                        kefuVo.setBingTime(DateUtil.formatDateTime(kefu.getBindtime()));
                    }
                    if(kefuVo.getState() != kefu.getState() ){
                        kefu.setState(kefuVo.getState());
                        if(kefu.getState() == 1 && kefu.getBindtime() == null){
                            Date date = new Date();
                            kefuVo.setBingTime(DateUtil.formatDateTime(date));
                            kefu.setBindtime(date);
                        }
                        subscrKefuDao.updateByPrimaryKeySelective(kefu);
                    }
                }
            }else{
                TSubscrKefu tSubscrKefu = new TSubscrKefu();
                tSubscrKefu.setState(kefuVo.getState());
                String bingTime = kefuVo.getBingTime();
                if(bingTime != null){
                    tSubscrKefu.setBindtime(DateUtil.parseDate(bingTime));
                }else if(kefuVo.getState() == 1){
                    Date date = new Date();
                    kefuVo.setBingTime(DateUtil.formatDateTime(date));
                    tSubscrKefu.setBindtime(date);
                }
                tSubscrKefu.setFace(kefuVo.getFace());
                tSubscrKefu.setWechatid(kefuVo.getWechatId());
                tSubscrKefu.setKefunum(kefuVo.getAccount());
                tSubscrKefu.setNickname(kefuVo.getNickName());
                tSubscrKefu.setAppid(appId);
                tSubscrKefu.setCreatetime(new Date());
                subscrKefuDao.insertSelective(tSubscrKefu);
            }
        }

        return list;
    }

    public void addKefu(SerializeMultipartFile file, String nickName, String account, String appId) {

        //这里还要获取一下公众号微信号的信息...........
        TSubscr model = subscrDao.selectByPrimaryKey(appId);
        if(model == null){
            throw new BusinessException("不存在此公众号");
        }
        account = account + "@" + model.getAlias();
        //拼接json
        String json = "{\"kf_account\" : \""+ account +"\", \"nickname\" : \""+ nickName +"\"}";
        //HttpConnUtil.doPostJson("", "");
        String ret = HttpConnUtil.doPostJson("https://api.weixin.qq.com/customservice/kfaccount/add?access_token=" + coreBiz.getToken(appId), json);
        this.ret(ret);

        TSubscrKefu kefu = new TSubscrKefu();
        kefu.setAppid(appId);
        kefu.setCreatetime(new Date());
        kefu.setNickname(nickName);
        kefu.setKefunum(account);
        kefu.setState(0);
        kefu.setWechatid(account.substring(account.lastIndexOf('@') + 1));
        subscrKefuDao.insertSelective(kefu);

        //开启客服回复
        /*TSubscrConfig subscrConfig = subscrConfigDao.selectByPrimaryKey(appId);
        if(subscrConfig != null && subscrConfig.getOpenkefu() == 0){
            subscrConfigDao.updateKefuAppid(appId);
        }*/
        //上传图像
        if(file != null){
            saveHeadImage(account, file, appId,kefu.getId());
        }
    }

    /*
        account: 完整的账号信息
     */
    public void saveHeadImage(String account, SerializeMultipartFile file, String appId,int id) {
        //这里还要获取一下公众号微信号的信息...........
        TSubscr model = subscrDao.selectByPrimaryKey(appId);
        if(model == null){
            throw new BusinessException("不存在此公众号");
        }

        String headImg = null;
        File tempFile = null;
        String uuName = UUID.randomUUID().toString();
        try {
            byte[] bytes = file.getBytes();
            tempFile = File.createTempFile(uuName,"." + FileUpOrDwUtil.getExtensionName(file.getOriginalFilename()));
            OutputStream output = new FileOutputStream(tempFile);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(bytes);

            headImg = FileUpOrDwUtil.uploadFileOrStream(tempFile, "kefu/" + tempFile.getName(), false);
            String ret = WxCurlUtil.postFile(" https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=" + coreBiz.getToken(appId) + "&kf_account=" + account, tempFile);
            TSubscrKefu kefu = new TSubscrKefu();
            kefu.setId(id);
            kefu.setFace(headImg);
            subscrKefuDao.updateByPrimaryKeySelective(kefu);
            this.ret(ret);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }finally {
            tempFile.deleteOnExit();
        }
    }

    public void deleteKefu(int id,String account, String appId) {
        try{
            //把阿里云上的图片删掉
            String ret = HttpConnUtil.doHttpRequest("https://api.weixin.qq.com/customservice/kfaccount/del?access_token=" + coreBiz.getToken(appId) + "&kf_account=" + account);
            subscrKefuDao.deleteByPrimaryKey(id);
            this.ret(ret);
        }catch (Exception e){
            throw  new BusinessException("不存在此["+ account +"]微信客服,无法删除");
        }
    }

    public void saveBindKefuWechat(int id, String account, String wechat, String appId) {
        //拼接json
        String json = "{\"kf_account\" : \""+ account +"\", \"invite_wx\" : \""+ wechat +"\"}";
        String ret = HttpConnUtil.doPostJson("https://api.weixin.qq.com/customservice/kfaccount/inviteworker?access_token=" + coreBiz.getToken(appId), json);
        this.ret(ret);
        TSubscrKefu kefu = new TSubscrKefu();
        kefu.setId(id);
        kefu.setState(2);//设置状态为邀请中状态
        subscrKefuDao.updateByPrimaryKeySelective(kefu);
    }

    public List<KefuStateVo> getKefuStateList(String appId) {

        List<KefuStateVo> list = new ArrayList<KefuStateVo>();

        if(RedisUtil.hasKey(kefu + appId)){
            List<Object> objects = RedisUtil.lGet("kf_online_list" + appId, 0, -1);
            list = (List<KefuStateVo>) objects.get(0);
        }else{
            String ret = HttpConnUtil.doHttpRequest("https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=" + coreBiz.getToken(appId));
            JSONObject jsonObject = JSONObject.fromObject(ret);
            JSONArray ke_list = jsonObject.getJSONArray("kf_online_list");
            for (int i=0; i<ke_list.size(); i++){
                JSONObject json = ke_list.getJSONObject(i);
                KefuStateVo vo = new KefuStateVo();
                vo.setAccount(json.getString("kf_account"));
                vo.setState(json.getInt("status") + "");
                vo.setChatNum(json.has("accepted_case") ? json.getInt("accepted_case") : 0);
                list.add(vo);
            }
            RedisUtil.lSet(kefu + appId, list, 5 * 60);
        }

        return list;
    }

    @Override
    public void doSyncKefu(String appId) {
        this.getKefus(appId);
    }

    public void ret(String ret){
        JSONObject jsonObj = JSONObject.fromObject(ret);
        if(jsonObj.has("errcode")){
            int errcode = jsonObj.getInt("errcode");
            if(errcode != 0){
                String s = WxException.map.get(errcode);
                throw new WxException(s);
            }
        }
    }
}
