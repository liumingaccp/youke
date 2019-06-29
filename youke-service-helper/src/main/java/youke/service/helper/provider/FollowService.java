package youke.service.helper.provider;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.constants.ApiCodeConstant;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.exception.H5SubscrException;
import youke.common.model.TFollowActive;
import youke.common.model.TFollowActivePoster;
import youke.common.model.TSubscrFans;
import youke.common.model.vo.param.helper.FollowOrderPosterVo;
import youke.common.model.vo.param.helper.FollowOrderQueryVo;
import youke.common.model.vo.param.helper.FollowQueryVo;
import youke.common.model.vo.result.helper.FollowOrderQueryRetVo;
import youke.common.model.vo.result.helper.FollowQueryRetVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.StringUtil;
import youke.facade.helper.provider.IFollowService;
import youke.facade.helper.utils.PoterUtils;
import youke.facade.helper.vo.Constants.ActiveState;
import youke.facade.helper.vo.FollowActiveVo;
import youke.facade.wx.provider.IWeixinService;
import youke.facade.wx.util.SceneType;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FollowService implements IFollowService {
    @Resource
    private IFollowActiveDao followActiveDao;
    @Resource
    private IFollowActiveOrderDao followActiveOrderDao;
    @Resource
    private IFollowActivePosterDao followActivePosterDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private IConfigDao configDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IWeixinService weixinService;

    @Override
    public int addActive(FollowActiveVo vo) {
        TFollowActive active = new TFollowActive();
        active.setCover(vo.getCover());
        active.setTitle(vo.getTitle());

        if(null == vo.getStartTime() || null == vo.getEndTime()){
            throw new BusinessException("任务开始或者结束时间不能为空");
        }
        active.setStarttime(DateUtil.parseDate(vo.getStartTime()));
        active.setEndtime(DateUtil.parseDate(vo.getEndTime()));

        Date today = DateUtil.parseDate(DateUtil.todayStartDate());
        if(active.getStarttime().getTime() < today.getTime()){
            throw new BusinessException("开始时间不能小于当前时间");
        }
        if(active.getStarttime().getTime() > active.getEndtime().getTime()){
            throw new BusinessException("结束时间要大于开始时间");
        }

        Integer id = vo.getId();
        TFollowActive active1 = null;
        if(id > 0){
            active1 = followActiveDao.selectByPrimaryKey(id);
        }
        List<Map<String, Object>> timeMap = followActiveDao.getActiveTimeList(active.getStarttime(), active.getEndtime(), vo.getYoukeId());
        if(timeMap != null && timeMap.size() > 0){
            if(id < 0){                                                                                                 //新增的时候除重
                throw new BusinessException( "活动时间存在冲突,请查询活动列表后重置活动时间");
            }else{
                for(Map<String, Object> date : timeMap){
                    Date startTime = (Date) date.get("startTime");
                    Date endTime = (Date)date.get("endTime");
                    if(!(active1.getStarttime().getTime() == startTime.getTime() && active1.getEndtime().getTime() == endTime.getTime())){
                        throw new BusinessException( "活动时间存在冲突,请查询活动列表后重置活动时间");                     //修改的时候除重
                    }
                }
            }
        }

        active.setBackMoney(vo.getBackMoney());
        active.setSlogan(vo.getSlogan());
        active.setFollowmsg(vo.getFollowMsg());
        active.setNoticemsg(vo.getNoticeMsg());
        active.setAppid(vo.getAppId());
        active.setYoukeid(vo.getYoukeId());
        active.setCreatetime(new Date());

        if(id > -1){
            Integer state = vo.getState();
            if((state != null && state != 0) || active1.getState() != 0){
                new BusinessException("活动已开始或者结束,不支持修改");
            }
            active.setId(id);
            followActiveDao.updateByPrimaryKeySelective(active);
        }else{
            int payState = subscrConfigDao.selectPayState(vo.getAppId());
            if(payState == 0){
                throw new BusinessException("尚未设置绑定微信支付");
            }
            active.setState(0);
            followActiveDao.insertSelective(active);
        }

        return active.getId();
    }

    @Override
    public void deleteActive(Integer activeId) {
        if(activeId != null && activeId > 0 ){
            Integer state = followActiveDao.selectState(activeId);
            if(state == null){
                throw new BusinessException("不存在"+ activeId +"对应的活动");
            }
            if(state == 0){
                followActiveDao.deleteByKey(activeId);
            }else if(state == 1 || state == 2){
                followActiveDao.updateState(activeId);
            }
        }
    }

    @Override
    public PageInfo<FollowQueryRetVo> queryList(FollowQueryVo params, String codePath) {

        String type = params.getType();
        if(type!= null && type.equals("H5")){
            String dyk = subscrDao.selectDyk(params.getAppId());
            params.setYoukeId(dyk);
            params.setState(1);
        }
        PageHelper.startPage(params.getPage(), params.getLimit(), "a.createTime desc");
        List<FollowQueryRetVo> list = followActiveDao.queryList(params);
        if(list != null && list.size() > 0){
            if("pc".equals(type)){
                for (FollowQueryRetVo vo : list){
                    String h5_taoke_page = configDao.selectVal("h5_follow_page");
                    //预览连接
                    String preUrl = h5_taoke_page.replace("{appId}", params.getAppId()) + "&id=" + vo.getId();
                    vo.setPreUrl(preUrl);
                    vo.setPreCodeUrl(codePath + URLEncoder.encode(preUrl));
                    //额外查询
                    int count = followActivePosterDao.queryCountByFollowId(vo.getId(), params.getAppId());
                    vo.setPosterNum(count);
                    Integer sum = followActiveOrderDao.querySum(vo.getId(), params.getAppId());
                    if(sum != null){
                        vo.setTotalCommision(sum);
                    }
                    vo.setStateDisplay(ActiveState.MAP_STATE.get(vo.getState()));
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<FollowOrderQueryRetVo> queryOrderList(FollowOrderQueryVo params) {
        String dyk = subscrDao.selectDyk(params.getAppId());
        PageHelper.startPage(params.getPage(), params.getLimit(), "torder.createTime desc");
        params.setYoukeId(dyk);
        List<FollowOrderQueryRetVo> list = followActiveOrderDao.queryList(params);
        return new PageInfo<>(list);
    }

    @Override
    public long saveFollowOrderPoster(FollowOrderPosterVo params) {
        if(!StringUtil.hasLength(params.getOpenId())
                || params.getActiveId() == null
                ){
            return -1;
        }
        String dyk = subscrDao.selectDyk(params.getAppId());
        if(!StringUtil.hasLength(dyk)){
            throw new BusinessException("[appId]参数错误");
        }
        TFollowActivePoster poster = new TFollowActivePoster();
        poster.setActiveid(params.getActiveId());
        poster.setAppid(params.getAppId());
        poster.setOpenid(params.getOpenId());
        poster.setCodeurl(params.getCodeUrl());
        poster.setCreatetime(new Date());
        poster.setYoukeid(dyk);
        followActivePosterDao.insertSelective(poster);
        return poster.getId();
    }

    public TFollowActivePoster selectPoter(Integer activeId, String openId){
        TFollowActivePoster model = followActivePosterDao.selectInfo(activeId, openId);
        return model;
    }

    @Override
    public void updateState(Integer activeId, String youkeId, Integer state) {
        if(activeId != null){
            TFollowActive info = followActiveDao.selectByPrimaryKeyAndYoukeId(activeId, youkeId);
            if(info != null){
                Integer state1 = info.getState();
                if(state == 1){
                    if(state1 != 0){
                        throw new BusinessException("["+ info.getTitle() +"] 活动处于非待开始 手动上线失败");
                    }else{
                        List<Map<String, Object>> list = followActiveDao.getActiveTimeList(DateUtil.parseDate(DateUtil.getStringDate()), info.getEndtime(), youkeId);
                        if(list != null && list.size() > 1){
                            String activeTitle = "";
                            for(Map<String, Object> map : list){
                                String title = (String)map.get("title");
                                activeTitle = activeTitle + "["+ title +"]";

                            }
                            throw new BusinessException("上线失败，与" + activeTitle + "推广关注活动时间冲突");
                        }
                        TFollowActive active = new TFollowActive();
                        active.setId(info.getId());
                        active.setState(state);
                        active.setStarttime(new Date());
                        followActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
                if(state == 2){
                    if(state1 != 1){
                        throw new BusinessException("["+ info.getTitle() +"] 活动处于非上线状态 手动下线失败");
                    }else{
                        TFollowActive active = new TFollowActive();
                        active.setId(info.getId());
                        active.setState(state);
                        followActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
            }
        }
    }

    @Override
    public TFollowActive getActiveDetail(Integer id, String appId, String dykId) {
        if(appId == null || id == null){
            throw new BusinessException("请传入对应查询的参数");
        }
        String dyk = dykId;
        if(!StringUtil.hasLength(dyk) && StringUtil.hasLength(appId)){
            dyk  = subscrDao.selectDyk(appId);
        }
        return followActiveDao.selectByPrimaryKeyAndYoukeId(id, dyk);
    }

    public String createPoter(Integer activeId, String openId) {
        if(activeId < 0){
            throw new BusinessException("传入正确的活动id");
        }
        if(!StringUtil.hasLength(openId)){
            throw new BusinessException("传入正确的活动openId");
        }
        TFollowActive active = followActiveDao.selectByPrimaryKey(activeId);
        TSubscrFans wxFans = subscrFansDao.selectByPrimaryKey(openId);
        if(wxFans == null){
            throw new H5SubscrException();
        }
        TFollowActivePoster model = followActivePosterDao.selectInfo(activeId, openId);
        if(model != null){
            model.setCreatetime(new Date());
            followActivePosterDao.updateByPrimaryKeySelective(model);
        }else {
            model = new TFollowActivePoster();
            model.setActiveid(activeId);
            model.setAppid(wxFans.getAppid());
            model.setOpenid(openId);
            model.setCodeurl(active.getSlogan());
            model.setCreatetime(new Date());
            model.setYoukeid(active.getYoukeid());
            followActivePosterDao.insertSelective(model);
        }
        String url = weixinService.getQrcodeTmp(wxFans.getAppid(), SceneType.FOLLOW, model.getId()+"",2592000);
        InputStream posterStream = HttpConnUtil.getStreamFromNetByUrl(active.getCover()+"?x-oss-process=image/resize,m_fill,h_600,w_600");
        InputStream fingerStream = HttpConnUtil.getStreamFromNetByUrl(ApiCodeConstant.OSS_BASE+"png/add94cd21712e55836a6aba74f8a3ac6.png");
        InputStream codeStream = HttpConnUtil.getStreamFromNetByUrl(ApiCodeConstant.DOMAIN_PCAPI+"common/qrcode?d="+ URLEncoder.encode(url));
        InputStream headImgInput = HttpConnUtil.getStreamFromNetByUrl(wxFans.getHeadimgurl());
        File resultFile = null;
        try {
            resultFile = File.createTempFile(UUID.randomUUID().toString(),".jpg");
            PoterUtils.graphicsGeneration(active.getSlogan(),DateUtil.formatDateTime(new Date()), headImgInput , posterStream, fingerStream, codeStream,  resultFile);
            return FileUpOrDwUtil.uploadLocalFile(resultFile, "haibao/" + resultFile.getName());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }finally {
            if(resultFile != null){
                resultFile.delete();
            }
            if(posterStream != null){
                try {
                    posterStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fingerStream != null){
                try {
                    fingerStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(codeStream != null){
                try {
                    codeStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(headImgInput != null){
                try {
                    headImgInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
