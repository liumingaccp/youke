package youke.service.helper.provider;

import com.csvreader.CsvWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.constants.ComeType;
import youke.common.constants.SubOrderMessageConstant;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.exception.H5MobileException;
import youke.common.exception.H5SubscrException;
import youke.common.model.*;
import youke.common.model.vo.param.helper.*;
import youke.common.model.vo.result.TrialActiveRetVo;
import youke.common.model.vo.result.helper.H5TtrialOrderDetailRetVo;
import youke.common.model.vo.result.helper.TrialWealOrderDetailRetVo;
import youke.common.model.vo.result.helper.TrialWealOrderQueryRetVo;
import youke.common.model.vo.result.helper.TrialWealQueryRetVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.queue.message.ActiveMassMessage;
import youke.common.queue.message.NewsMsgMessage;
import youke.common.utils.DateUtil;
import youke.common.utils.StringUtil;
import youke.facade.helper.provider.ITrialWealService;
import youke.facade.helper.vo.Constants.ActiveState;
import youke.facade.helper.vo.Constants.OrderState;
import youke.facade.helper.vo.DemoPics;
import youke.facade.helper.vo.TrialWealActiveVo;
import youke.service.helper.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 12:04
 */
@Service
public class TrialWealService extends Base implements ITrialWealService {
    @Resource
    private ITrialActiveDao trialActiveDao;
    @Resource
    private ITrialActivePicDao trialActivePicDao;
    @Resource
    private ITrialActiveOrderDao trialActiveOrderDao;
    @Resource
    private ITrialActiveOrderPicDao trialActiveOrderPicDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private ISubscrFansIntegralDao subscrFansIntegralDao;
    @Resource
    private IConfigDao configDao;
    @Resource
    private ITrialActiveExamimgDao trialActiveExamimgDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IShopDao shopDao;
    @Resource
    private QueueSender queueSender;

    @Override
    public int addActive(TrialWealActiveVo vo) {
        TTrialActive active = new TTrialActive();
        active.setShopid(vo.getShopId());
        active.setGoodsid(vo.getGoodsId());
        active.setGoodsurl(vo.getGoodsUrl());
        active.setCover(vo.getCover());
        active.setTitle(vo.getTitle());
        active.setState(0);

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

        Integer shopId = vo.getShopId();
        if(shopId == null || shopId < 0){
            throw new BusinessException("请选择店铺");
        }
        TShop shop = shopDao.selectByPrimaryKey(shopId);
        if(shop==null || shop.getState()== null){
            throw new BusinessException("请选择店铺");
        }else {
            if(shop.getState() != 1){
                throw new BusinessException("店铺:"+shop.getTitle()+"已过期或者未授权");
            }
        }

        if(vo.getPrice() == null || vo.getPrice() <= 0){
            throw new BusinessException("价格不能小于0");
        }
        if(vo.getTotalNum() == null || vo.getTotalNum() <= 0){
            throw new BusinessException("数量不能小于0");
        }
        if(vo.getBackReward() == null || vo.getBackReward() <= 0){
            throw new BusinessException("返利不能小于0");
        }

        active.setPrice(vo.getPrice());
        active.setRewardtype(vo.getRewardType());
        active.setBackreward(vo.getBackReward());
        active.setTotalnum(vo.getTotalNum());
        active.setCostintegral(vo.getCostIntegral());
        active.setFanslimit(vo.getFansLimit());
        active.setSexlimit(vo.getSexLimit());
        active.setOpenexamineimg(vo.getOpenExamineImg());
        active.setExamineimg(vo.getExamineImg());
        active.setWaitday(vo.getWaitDay());
        active.setOpentype(vo.getOpenType());
        active.setNum(vo.getTotalNum());
        active.setCreatetime(new Date());
        active.setYoukeid(vo.getYoukeId());
        active.setIntro(JSONUtils.valueToString(vo.getIntro()));

        Integer id = vo.getId();
        if(id != null && id > -1){
            Integer state = vo.getState();
            TTrialActive active1 = trialActiveDao.selectByPrimaryKey(id);
            if(active1 == null){
                throw new BusinessException("不存在此活动");
            }
            if((state != null && state != 0) || active1.getState() != 0){
                new BusinessException("活动已开始或者结束,不支持修改");
            }
            active.setId(vo.getId());
            trialActiveDao.updateByPrimaryKeySelective(active);
            trialActivePicDao.deleteByActive(active.getId(), active.getYoukeid());
        }else{
            int payState = subscrConfigDao.selectPayState(vo.getAppId());
            if(payState == 0){
                throw new BusinessException("尚未设置绑定微信支付");
            }
            trialActiveDao.insertSelective(active);
        }


        JSONArray demoPics = vo.getDemoPics();
        if(demoPics != null && demoPics.size() > 0){
            for(int i=0; i<demoPics.size(); i++){
                DemoPics pic = (DemoPics) JSONObject.toBean(demoPics.getJSONObject(i), DemoPics.class);
                TTrialActivePic model = new TTrialActivePic();
                model.setPicname(pic.getPicName());
                model.setPicinfo(pic.getPicInfo());
                model.setPicurl(pic.getPicUrl());
                model.setType(pic.getType());
                model.setActiveid(Long.valueOf(active.getId()));
                model.setYoukeid(vo.getYoukeId());
                trialActivePicDao.insertSelective(model);
            }
        }

        return active.getId();
    }

    @Override
    public void deleteActive(Integer activeId) {
        if(activeId != null && activeId > 0 ){
            Integer state = trialActiveDao.selectState(activeId);
            if(state == null){
                throw new BusinessException("不存在"+ activeId +"对应的活动");
            }
            if(state == 0){
                trialActiveDao.deleteByKey(activeId);
            }else if(state == 1 || state == 2){
                trialActiveDao.updateState(activeId);
            }
        }
    }

    @Override
    public PageInfo<TrialWealQueryRetVo> queryList(TrialWealQueryVo params, String codePath) {
        String type = params.getType();
        if(type != null && type.equals("H5")){
            String dyk = subscrDao.selectDyk(params.getAppId());
            params.setState(1);
            params.setYoukeId(dyk);
        }
        PageHelper.startPage(params.getPage(), params.getLimit(), "active.createTime desc");

        List<TrialWealQueryRetVo> list = trialActiveDao.queryList(params);
        if(list != null && list.size() > 0){
            if("H5".equals(type)){
                for (TrialWealQueryRetVo vo : list){
                    //截图个数
                    /*Integer demoCount = trialActivePicDao.selectPicsCount(vo.getId(), params.getYoukeId());
                    if(demoCount == null){
                        vo.setDemoPicCount(0);
                    }else{
                        vo.setDemoPicCount(demoCount);
                    }*/
                    String goodsurl = vo.getGoodsUrl();
                    if(StringUtil.hasLength(goodsurl)){
                        if(goodsurl.contains("taobao.com")){
                            vo.setShopType(0);
                        }else if(goodsurl.contains("tmall.com")){
                            vo.setShopType(1);
                        } else if(goodsurl.contains("jd.com")){
                            vo.setShopType(2);
                        }
                    }

                    vo.setStateDisplay(ActiveState.MAP_STATE.get(vo.getState()));
                }
            }else if("pc".equals(type)){
                String h5_taoke_page = configDao.selectVal("h5_trial_page");
                for (TrialWealQueryRetVo vo : list){
                    //预览连接
                    String preUrl = h5_taoke_page.replace("{appId}", params.getAppId()) + "&id=" + vo.getId();
                    vo.setPreUrl(preUrl);
                    vo.setPreCodeUrl(codePath + URLEncoder.encode(preUrl));
                    vo.setStateDisplay(ActiveState.MAP_STATE.get(vo.getState()));

                    //成功参与和参与
                    Integer joinCount = trialActiveOrderDao.selectJoinCount(vo.getId(), params.getYoukeId(), -1);
                    Integer successNum = trialActiveOrderDao.selectJoinCount(vo.getId(), params.getYoukeId(), 1);
                    vo.setJoinNum(joinCount);
                    vo.setSuccessNum(successNum);
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<TrialWealOrderQueryRetVo> queryOrderList(TrialWealOrderQueryVo params) {
        if(params.getType() != null && params.getType().equals("H5")){
            String dyk = subscrDao.selectDyk(params.getAppId());
            params.setYoukeId(dyk);
        }
        PageHelper.startPage(params.getPage(), params.getLimit(), "torder.createTime desc");
        List<TrialWealOrderQueryRetVo> list = trialActiveOrderDao.queryList(params);
        if(list != null && list.size() > 0){
            for (TrialWealOrderQueryRetVo vo : list){
                if("H5".equals(params.getType())){
                    //截图个数
                    Integer demoCount = trialActivePicDao.selectPicsCount(vo.getActiveId(), params.getYoukeId());
                    if(demoCount == null){
                        vo.setDemoPicCount(0);
                    }else{
                        vo.setDemoPicCount(demoCount);
                    }
                }
                vo.setStateDisplay(OrderState.MAP_STATE.get(vo.getState()));
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public void examinBatch(TrialWealOrderExamineParam param) {
        String orderIds = param.getOrderIds();
        if(orderIds == null){
            throw new BusinessException("请选择审核的订单");
        }
        List<String> list = Arrays.asList(orderIds.split(","));
        param.setOrderIdList(list);
        trialActiveOrderDao.updateStateBatch(param);
    }

    @Override
    public TrialWealOrderDetailRetVo getExamineDetail(Long orderId, String appId) {
        TrialWealOrderDetailRetVo detail = trialActiveOrderDao.selectExamineDetail(orderId, appId);
        if(detail != null){
            detail.setStateDisplay(OrderState.MAP_STATE.get(detail.getState()));
            List<String> pics = trialActiveOrderPicDao.queryListByOrderId(orderId);
            if(pics != null && pics.size() > 0){
                StringBuffer sb = new StringBuffer();
                for(String str : pics){
                    sb.append(str + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
                detail.setUserPics(sb.toString());
            }
        }
        return detail;
    }

    @Override
    public String doImportOrder(TrialWealOrderQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit(), "torder.createTime desc");
        List<TrialWealOrderQueryRetVo> list = trialActiveOrderDao.queryList(params);

        if(list != null && list.size() > 0){
            for (TrialWealOrderQueryRetVo vo : list){
                vo.setStateDisplay(OrderState.MAP_STATE.get(vo.getState()));
            }
        }else{
            throw new BusinessException("导入数据为空请重新设置筛选条件");
        }
        File temp = null;
        FileOutputStream stream = null;
        String path = null;

        try {
            temp = File.createTempFile(UUID.randomUUID().toString(), ".csv");
            stream = new FileOutputStream(temp);
            if(temp != null){
                this.doImportOrder(list, stream);
            }
            path = FileUpOrDwUtil.uploadLocalFile(temp, "helper/order/" + temp.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(temp != null){
                temp.delete();
            }
        }
        return path;
    }

    @Override
    public TrialActiveRetVo getDetail(String appId, Integer id, String dykId) {
        if(appId == null || id == null){
            throw new BusinessException("请传入对应查询的参数");
        }
        TrialActiveRetVo vo = null;
        String dyk = dykId;
        if(!StringUtil.hasLength(dyk) && StringUtil.hasLength(appId)){
            dyk  = subscrDao.selectDyk(appId);
            vo = trialActiveDao.selectByPrimaryKeyAndYoukeId(id, dyk);

        }else{
            vo = trialActiveDao.selectByPrimaryKeyAndYoukeId(id, dyk);
            H5QueryPicVo params = new H5QueryPicVo();
            params.setYoukeId(dyk);
            params.setAppId(appId);
            params.setActiveId(id);
            List<TTrialActivePic> list = trialActivePicDao.selectPics(params);
            vo.setDemoPics(list);
        }
        if(vo != null){
            String goodsurl = vo.getGoodsurl();
            if(StringUtil.hasLength(goodsurl)){
                if(goodsurl.contains("taobao.com")){
                    vo.setShopType(0);
                }else if(goodsurl.contains("tmall.com")){
                    vo.setShopType(1);
                } else if(goodsurl.contains("jd.com")){
                    vo.setShopType(2);
                }
            }
            if(vo.getIntros() != null && vo.getIntros().startsWith("[")){
                vo.setIntro(JSONArray.fromObject(vo.getIntros()));
            }
        }
        return vo;
    }

    @Override
    public List<DemoPics> getTrialExamplePic(H5QueryPicVo params) {
        String dyk = subscrDao.selectDyk(params.getAppId());
        params.setYoukeId(dyk);
        List<TTrialActivePic> list = trialActivePicDao.selectPics(params);
        List<DemoPics> pics = null;
        if(list != null && list.size() > 0){
            pics = new ArrayList<>();
            for (TTrialActivePic vo :list){
                DemoPics pic = new DemoPics();
                pic.setPicUrl(vo.getPicurl());
                pic.setType(vo.getType());
                pic.setPicName(vo.getPicname());
                pic.setPicInfo(vo.getPicinfo());
                pics.add(pic);
            }
        }
        return pics;
    }

    @Override
    public void saveOrderNum(String appId, String openId, Long orderId, String orderNo) {
        String dyk = subscrDao.selectDyk(appId);
        if(dyk == null){
            throw new BusinessException("参数异常");
        }

        TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(openId);
        if(subscrFans == null){
            throw new BusinessException("请在微信端打开");
        }


        if(subscrFans.getState() == 1){
            throw new BusinessException("请联系公众号管理员");
        }

        if(orderId == null){
            throw new BusinessException("请传入订单号");
        }
        Map<String, Object> detail = trialActiveOrderDao.selectOrderDetail(orderId);
        if(detail == null){
            throw new BusinessException("不存在此订单,无效的订单号");
        }
        String goodId = (String)detail.get("goodsId");
        Integer orderState = (Integer) detail.get("state");
        String order_openId = (String)detail.get("openId");
        Object fansLimit = detail.get("fansLimit");
        if(order_openId == null || !order_openId.equals(openId)){
            throw new BusinessException("参与者和提交订单号者不同");
        }

        if(subscrFans == null || subscrFans.getSubstate() == 1){
            throw new H5SubscrException();
        }
        if(fansLimit != null){
            int fansLimitInt = (Integer) fansLimit;
            if(fansLimitInt == 1 && empty(subscrFans.getMobile())){
                throw new H5MobileException();
            }
        }

        Integer count = trialActiveOrderDao.selectCount(orderNo, dyk);
        if(count != null && count > 0){
            throw new BusinessException("【该订单号已参与过一次试用福利活动】");
        }

        TShopOrder shopOrder = shopOrderDao.selectByOrderno(orderNo);
        if(shopOrder == null || !shopOrder.getGoodid().equals(goodId)){
            throw new BusinessException("【订单对应商品与本活动商品不匹配】");
        }
        if(orderState == 1){
            throw new BusinessException("【该活动记录超时未购买已失效】");
        }else if(orderState > 1){
            throw new BusinessException("【该活动记录已绑定订单号】");
        }
        Integer state = shopOrder.getState();
        if(state == 0 || state == 5 || state ==6){
            throw new BusinessException("【只能提交已付款的订单号】");
        }
        TTrialActiveOrder order = new TTrialActiveOrder();
        order.setId(orderId);
        order.setState(2);
        order.setBuyername(shopOrder.getBuyername());
        order.setOrderno(orderNo);
        order.setOrdertime(new Date());
        trialActiveOrderDao.updateByPrimaryKeySelective(order);
    }

    @Override
    public H5TtrialOrderDetailRetVo getTrialOrderDetail(String appId, Long id) {
        TTrialActiveOrder order = trialActiveOrderDao.selectByPrimaryKey(id);
        H5TtrialOrderDetailRetVo vo = new H5TtrialOrderDetailRetVo();
        if(order != null){
            List<TTrialActiveOrderPic> pics = trialActiveOrderPicDao.queryPicsByOrderId(order.getId(), order.getActiveid());
            vo.setPics(pics);
        }
        return vo;
    }

    @Override
    public long saveAccountPic(Long activeId, String appId, String opneId, String fileUrl) {
        StringBuffer buffer = new StringBuffer();
        if(activeId == null || activeId < 0){
            buffer.append("activeId参数 不能为空;");
        }
        if(appId == null){
            buffer.append("appId参数 不能为空;");
        }
        if(opneId == null){
            buffer.append("openId参数 不能为空;");
        }
        if(fileUrl == null){
            buffer.append("上传截图路径 不能为空");
        }

        TTrialActiveExamimg info = new TTrialActiveExamimg();
        info.setActiveid(activeId);
        info.setAppid(appId);
        info.setOpenid(opneId);
        info.setPicurl(fileUrl);
        info.setCreatetime(new Date());

        trialActiveExamimgDao.insertSelective(info);
        return info.getId();
    }

    @Override
    public long saveTrialOrder(HelperOrderVo params) {
        if(!StringUtil.hasLength(params.getAppId())
                || params.getActiveId() == null  || params.getActiveId() <0
                || !StringUtil.hasLength(params.getOpenId())
                ){
            throw new BusinessException("请检查传入参数是否为空");
        }
        String dyk = subscrDao.selectDyk(params.getAppId());
        if(!StringUtil.hasLength(dyk)){
            throw new BusinessException("[appId]参数错误");
        }
        TTrialActive active = trialActiveDao.selectByPrimaryKey(params.getActiveId());
        Integer integral = active.getCostintegral();
        if(active == null){
            throw new BusinessException("不存在此活动");
        }
        if(active.getNum() == 0){
            throw new BusinessException("活动已结束");
        }
        TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(params.getOpenId());

        if(subscrFans.getState() == 1){
            throw new BusinessException("请联系公众号管理员");
        }
        if(subscrFans == null || subscrFans.getSubstate() == 1){
            throw new H5SubscrException();
        }
        if(active.getFanslimit() != null){
            if(active.getFanslimit() == 1 &&  empty(subscrFans.getMobile())){
                throw new H5MobileException();
            }
        }
        //如果消耗积分,创建积分流水
        if(integral > 0){

            //扣除对应的积分
            if(subscrFans.getIntegral() < integral){
                throw new BusinessException("积分不足.所需积分:"+integral + "当前积分:"+ subscrFans.getIntegral());
            }

            ActiveMassMessage message = new ActiveMassMessage();
            message.setAppId(params.getAppId());
            message.setOpenId(params.getOpenId());
            message.setComeType(ComeType.SHI_YONG_FU_LI);
            message.setIntegral(-integral);
            message.setTitle(active.getTitle());
            message.setRecordId(Long.valueOf(active.getId()+""));
            message.setYoukeId(active.getYoukeid());
            message.setMoney(null);
            message.setState(1);
            queueSender.send("activemass.queue", message);

        }

        TTrialActiveOrder orderVo = new TTrialActiveOrder();
        orderVo.setActiveid(active.getId());
        orderVo.setAppid(params.getAppId());
        orderVo.setBackreward(active.getBackreward());
        orderVo.setRewardtype(active.getRewardtype());
        orderVo.setOpenid(params.getOpenId());
        orderVo.setPrice(active.getPrice());
        orderVo.setShopid(active.getShopid()+"");
        orderVo.setTitle(active.getTitle());
        orderVo.setWxfansname(subscrFans.getNickname());
        orderVo.setCreatetime(new Date());
        orderVo.setYoukeid(dyk);
        orderVo.setState(0);
        trialActiveOrderDao.insertSelective(orderVo);

        TTrialActive model = new TTrialActive();
        model.setId(active.getId());
        model.setNum(active.getNum() - 1);
        if (model.getNum() == 0) {
            model.setState(2);
        }
        trialActiveDao.updateByPrimaryKeySelective(model);

        Map<String, String> msgMap = SubOrderMessageConstant.TYPE.get(SubOrderMessageConstant.SHI_YONG);
        //推送消息,提供填写订单号入口
        NewsMsgMessage newsMsgMessage = new NewsMsgMessage();
        newsMsgMessage.setAppId(params.getAppId());
        newsMsgMessage.setDescription(SubOrderMessageConstant.DESCRIPTION.replace("{TITLE}",active.getTitle()));
        newsMsgMessage.setOpenId(params.getOpenId());
        newsMsgMessage.setPicUrl(active.getCover());
        newsMsgMessage.setTitle(msgMap.get("title"));
        newsMsgMessage.setUrl(msgMap.get("url").replace("{APPID}", params.getAppId()));
        queueSender.send("newsmsg.queue", newsMsgMessage);

        return orderVo.getId();
    }

    @Override
    public void saveTrialOrderPic(TrialOrderPicsVo params) {
        Long activeId = params.getActiveId();
        String appId = params.getAppId();
        String openId = params.getOpenId();
        Long orderId = params.getOrderId();
        JSONArray demoPics = params.getDemoPics();
        if(activeId == null || orderId == null || appId == null || openId == null || demoPics == null){
            throw new BusinessException("检查传入的参数是否为空");
        }
        String dyk = subscrDao.selectDyk(appId);

        List<TTrialActiveOrderPic> pics = trialActiveOrderPicDao.queryPicsByOrderId(orderId, Integer.parseInt(activeId + ""));
        if(pics != null && pics.size() > 0){
            throw new BusinessException("您已提交过使用截图");
        }

        for(int i=0; i<demoPics.size();i++){
            DemoPics pic = (DemoPics) JSONObject.toBean(demoPics.getJSONObject(i), DemoPics.class);
            TTrialActiveOrderPic model = new TTrialActiveOrderPic();
            model.setActiveid(activeId);
            model.setOrderid(orderId);
            model.setYoukeid(dyk);
            model.setPicinfo(pic.getPicInfo());
            model.setPicname(pic.getPicName());
            model.setPicurl(pic.getPicUrl());
            model.setType(pic.getType());
            trialActiveOrderPicDao.insertSelective(model);
        }
    }

    @Override
    public String getAccountExaminPic(long activeId,String openId, String appId) {
        return  trialActiveExamimgDao.selectPic(activeId,openId, appId);
    }

    @Override
    public void updateState(Integer activeId, String youkeId, Integer state) {
        if(activeId != null){
            TrialActiveRetVo info = trialActiveDao.selectByPrimaryKeyAndYoukeId(activeId, youkeId);
            if(SystemColor.info != null){
                Integer state1 = info.getState();
                if(state == 1){
                    if(state1 != 0){
                        throw new BusinessException("["+ info.getTitle() +"] 活动处于非待开始 手动上线失败");
                    }else{
                        TTrialActive active = new TTrialActive();
                        active.setId(info.getId());
                        active.setState(state);
                        active.setStarttime(new Date());
                        trialActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
                if(state == 2){
                    if(state1 != 1){
                        throw new BusinessException("["+ info.getTitle() +"] 活动处于非上线状态 手动下线失败");
                    }else{
                        TTrialActive active = new TTrialActive();
                        active.setId(info.getId());
                        active.setState(state);
                        trialActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
            }
        }
    }

    @Override
    public TrialWealOrderQueryRetVo getOrderInfo(long orderId, String openId, String appId) {
        if(!StringUtil.hasLength(appId)
                || !StringUtil.hasLength(openId)){
            return null;
        }
        TrialWealOrderQueryRetVo vo = trialActiveOrderDao.selectOrderInfo(appId, openId, orderId);
        if(vo != null){
            //截图个数
            String dyk = subscrDao.selectDyk(appId);
            Integer demoCount = trialActivePicDao.selectPicsCount(vo.getActiveId(), dyk);
            if(demoCount == null){
                vo.setDemoPicCount(0);
            }else{
                vo.setDemoPicCount(demoCount);
            }
        }
        return vo;
    }

    private static void doImportOrder(List<TrialWealOrderQueryRetVo> orderList, OutputStream outputStream){
        //写入临时文件
        //表头
        String[] headers = new String[]{
                "记录编号",
                "商品名称",
                "店铺名称",
                "淘宝账户",
                "微信昵称",
                "购物订单号",
                "店铺类型",
                "提交时间",
                "奖励类型",
                "返利奖励（单位分）/积分",
                "返利时间",
                "返利状态"
        };
        String[] content = new String[12];
        try {
            CsvWriter csvWriter = new CsvWriter(outputStream, ',', Charset.forName("GBK"));
            csvWriter.writeRecord(headers);
            for(TrialWealOrderQueryRetVo vo : orderList){
                content[0] = vo.getId() + "";
                content[1] = vo.getTitle();
                content[2] = vo.getShopName();
                content[3] = vo.getBuyerName();
                content[4] = vo.getWxFansName();
                content[5] = vo.getOrderNo();
                content[6] = getShopTypeDisplay(vo.getShopType());
                content[7] = DateUtil.formatDate(vo.getCreateTime());
                content[8] = getRewardTypeDisplay(Integer.parseInt(vo.getRewardType()));
                content[9] = vo.getBackReward() + "";
                content[10] = vo.getBackTime() != null ?DateUtil.formatDate(vo.getBackTime()):null;
                content[11] = vo.getStateDisplay();
                csvWriter.writeRecord(content);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getShopTypeDisplay(int type){
        switch (type){
            case 0 : return "淘宝";
            case 1 : return "天猫";
            case 2 : return "京东";
            default: return "类型错误";
        }
    }

    private static String getRewardTypeDisplay(int type){
        switch (type){
            case 0 : return "红包";
            case 1 : return "积分";
            default: return "类型错误";
        }
    }
}
