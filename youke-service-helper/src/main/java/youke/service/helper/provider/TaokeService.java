package youke.service.helper.provider;

import com.alibaba.fastjson.JSON;
import com.csvreader.CsvWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;
import org.springframework.stereotype.Service;
import youke.common.constants.ApiCodeConstant;
import youke.common.constants.SubOrderMessageConstant;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.exception.H5MobileException;
import youke.common.exception.H5SubscrException;
import youke.common.model.*;
import youke.common.model.vo.page.Page;
import youke.common.model.vo.page.PageResult;
import youke.common.model.vo.param.helper.*;
import youke.common.model.vo.result.helper.*;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.queue.message.NewsMsgMessage;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.StringUtil;
import youke.facade.helper.provider.ITaokeService;
import youke.facade.helper.utils.PoterUtils;
import youke.facade.helper.vo.Constants.OrderState;
import youke.facade.helper.vo.TaokeActiveVo;
import youke.service.helper.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 12:04
 */
@Service
public class TaokeService implements ITaokeService {
    @Resource
    private ITaokeActiveDao taokeActiveDao;
    @Resource
    private ITaokeActiveOrderDao taokeActiveOrderDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private IConfigDao configDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IShopDao shopDao;
    @Resource
    private QueueSender queueSender;

    @Override
    public int addActive(TaokeActiveVo vo) {
        TTaokeActive active = new TTaokeActive();
        active.setShopid(vo.getShopId());
        active.setCover(vo.getCover());
        active.setGoodsid(vo.getGoodsId());
        active.setGoodsurl(vo.getGoodsUrl());
        active.setCover(vo.getCover());
        active.setTitle(vo.getTitle());
        active.setState(0);

        if (null == vo.getStartTime() || null == vo.getEndTime()) {
            throw new BusinessException("任务开始或者结束时间不能为空");
        }
        active.setStarttime(DateUtil.parseDate(vo.getStartTime()));
        active.setEndtime(DateUtil.parseDate(vo.getEndTime()));
        Date today = DateUtil.parseDate(DateUtil.todayStartDate());
        if(active.getStarttime().getTime() < today.getTime()){
            throw new BusinessException("开始时间不能小于当前时间");
        }
        if (active.getStarttime().getTime() > active.getEndtime().getTime()) {
            throw new BusinessException("结束时间要大于开始时间");
        }

        if(active.getStarttime().getTime() > active.getEndtime().getTime()){
            throw new BusinessException("结束时间要大于开始时间");
        }
        if(vo.getPrice() == null || vo.getPrice() <= 0){
            throw new BusinessException("价格不能小于0");
        }
        if(vo.getTotalNum() == null || vo.getTotalNum() <= 0){
            throw new BusinessException("数量不能小于0");
        }
        if(vo.getBackMoney() == null || vo.getBackMoney() <= 0){
            throw new BusinessException("返利不能小于0");
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


        active.setPrice(vo.getPrice());
        active.setBackmoney(vo.getBackMoney());
        active.setCommision(vo.getCommision());
        active.setOpenorderlimit(vo.getOpenOrderLimit());
        active.setOpenbacklimit(vo.getOpenBackLimit());
        active.setOpenselfcommision(vo.getOpenSelfCommision());
        active.setTotalnum(vo.getTotalNum());
        active.setBackbg(vo.getBackBg());
        active.setFanslimit(vo.getFansLimit());
        active.setWaitday(vo.getWaitDay());
        active.setSlogan(vo.getSlogan());
        active.setOpentype(vo.getOpenType());
        active.setIntro(JSONUtils.valueToString(vo.getIntro()));
        active.setNum(vo.getTotalNum());
        active.setCreatetime(new Date());
        active.setYoukeid(vo.getYoukeId());
        active.setTaocode(vo.getTaoCode());

        Integer id = vo.getId();
        if(id != null && id > -1){
            Integer state = vo.getState();
            TTaokeActive active1 = taokeActiveDao.selectByPrimaryKey(id);
            if(active1 == null){
                throw new BusinessException("不存在此活动");
            }
            if((state != null && state != 0) || active1.getState() != 0){
                new BusinessException("活动已开始或者结束,不支持修改");
            }
            active.setId(id);
            taokeActiveDao.updateByPrimaryKeySelective(active);
        }else{
            int payState = subscrConfigDao.selectPayState(vo.getAppId());
            if(payState == 0){
                throw new BusinessException("尚未设置绑定微信支付");
            }
            taokeActiveDao.insertSelective(active);
        }
        return active.getId();
    }

    @Override
    public void deleteActive(Integer activeId) {
        if (activeId != null && activeId > 0) {
            Integer state = taokeActiveDao.selectState(activeId);
            if (state == null) {
                throw new BusinessException("不存在" + activeId + "对应的活动");
            }
            if (state == 0) {
                taokeActiveDao.deleteByKey(activeId);
            } else if (state == 1 || state == 2) {
                taokeActiveDao.updateState(activeId);
            }
        }
    }

    @Override
    public PageInfo<TaokeQueryRetVo> queryList(TaokeQueryVo params, String codePath) {

        String type = params.getType();
        if (type != null && type.equals("H5")) {
            params.setState(1);
            String dyk = subscrDao.selectDyk(params.getAppId());
            params.setYoukeId(dyk);
        }
        PageHelper.startPage(params.getPage(), params.getLimit(), "createTime desc");
        List<TaokeQueryRetVo> list = taokeActiveDao.queryList(params);
        if (list != null && list.size() > 0) {
            if ("pc".equals(type)) {
                for (TaokeQueryRetVo vo : list) {
                    String h5_taoke_page = configDao.selectVal("h5_taoke_page");
                    String preUrl = h5_taoke_page.replace("{appId}", params.getAppId()) + "&id=" + vo.getId();
                    vo.setPreUrl(preUrl);
                    vo.setPreCodeUrl(codePath + URLEncoder.encode(preUrl));
                    Integer count = taokeActiveDao.queryCount(vo.getId());
                    vo.setJoinNum(count);
                    TaokeCountDataVo data = taokeActiveDao.selectCountData(vo.getId());
                    if (data != null) {
                        vo.setTotalBackMoney(data.getTotalBackMoney());
                        vo.setTotalCommision(data.getTotalCommision());
                        vo.setBackNum(data.getBackNum());
                    }
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<TaokeOrderQueryRetVo> queryOrderList(TaokeOrderQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit(), "torder.createTime desc");
        List<TaokeOrderQueryRetVo> list = taokeActiveOrderDao.queryList(params);
        if (list != null && list.size() > 0) {
            for (TaokeOrderQueryRetVo vo : list) {
                String taokeOpenId = vo.getTaokeOpenId();
                Integer activeId = vo.getActiveId();
                vo.setTaokeTime((String) RedisSlaveUtil.get("taoke_" + taokeOpenId + "_" + activeId));
                vo.setStateDisplay(OrderState.MAP_STATE.get(vo.getState()));
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageResult<H5TaokeOrderQueryRetVo> queryOrderList(H5TaokeOrderQueryVo params) {
        PageResult<H5TaokeOrderQueryRetVo> pageResult = new PageResult<>();
        Integer count = taokeActiveOrderDao.queryCountForOpenId(params);
        if (count == null || count == 0) {
            return null;
        }
        Page page = new Page();
        page.setPageNo(params.getPage());
        page.setPageSize(params.getLimit());
        page.setTotalRecords(count);

        List<H5TaokeOrderQueryRetVo> list = taokeActiveOrderDao.queryOrderListByOenId(params);
        if(list != null && list.size() > 0){
            for(H5TaokeOrderQueryRetVo vo : list){
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
            }
        }
        pageResult.setList(list);
        pageResult.setPage(page);
        return pageResult;
    }

    @Override
    public void examinBatch(TaokeOrderExamineParam param) {
        String orderIds = param.getOrderIds();
        if (orderIds == null) {
            throw new BusinessException("请选择审核的订单");
        }
        List<String> list = Arrays.asList(orderIds.split(","));
        param.setOrderIdList(list);
        taokeActiveOrderDao.updateStateBatch(param);
    }

    @Override
    public TaokeOrderDetailRetVo getExamineDetail(Integer orderId, String appId) {
        TaokeOrderDetailRetVo detail = taokeActiveOrderDao.selectExamineDetail(orderId, appId);
        if (detail != null) {
            detail.setStateDisplay(OrderState.MAP_STATE.get(detail.getState()));
        }
        return detail;
    }

    @Override
    public String doImportOrder(TaokeOrderQueryVo params) {
        PageInfo<TaokeOrderQueryRetVo> info = this.queryOrderList(params);
        List<TaokeOrderQueryRetVo> list = info.getList();
        File temp = null;
        FileOutputStream stream = null;
        String path = null;
        if (list == null || list.size() <= 0) {
            throw new BusinessException("导出数据为空请重新设置筛选条件");
        }
        try {
            temp = File.createTempFile(UUID.randomUUID().toString(), ".csv");
            stream = new FileOutputStream(temp);
            if (temp != null) {
                this.doImportOrder(list, stream);
            }
            path = FileUpOrDwUtil.uploadLocalFile(temp, "helper/order/" + temp.getName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (temp != null) {
                temp.delete();
            }
        }
        return path;
    }

    @Override
    public youke.common.model.vo.result.TaokeActiveVo getAtiveDetail(Integer activeId, String appId, String youkeId) {
        if (activeId == null || activeId < 0) {
            return null;
        }
        String dyk = youkeId;
        if(!StringUtil.hasLength(dyk) && StringUtil.hasLength(appId)){
            dyk  = subscrDao.selectDyk(appId);
        }
        youke.common.model.vo.result.TaokeActiveVo taokeActiveVo = taokeActiveDao.selectDetail(activeId, dyk);
        if(taokeActiveVo != null){
            if(taokeActiveVo.getIntros() != null && taokeActiveVo.getIntros().startsWith("[")){
                taokeActiveVo.setIntro(JSONArray.fromObject(taokeActiveVo.getIntros() ));
            }
        }
        return taokeActiveVo;
    }

    @Override
    public long saveTaokeOrder(HelperOrderVo params) {
       if (!StringUtil.hasLength(params.getAppId() )
                || params.getActiveId() == null || params.getActiveId() < 0
                || !StringUtil.hasLength(params.getOpenId())
                || !StringUtil.hasLength(params.getTaokeOpenId())
                ) {
            throw new BusinessException("请检查传入参数是否为空");
        }
        String dyk = subscrDao.selectDyk(params.getAppId());
        if(!StringUtil.hasLength(dyk)){
            throw new BusinessException("[appId]参数错误");
        }
        TTaokeActive active = taokeActiveDao.selectByPrimaryKey(params.getActiveId());
        if (active == null) {
            throw new BusinessException("不存在此活动");
        }
        if(active.getState() == 2){
            throw new BusinessException("活动已经结束");
        }
        if (active.getNum() == 0) {
            throw new BusinessException("活动已经结束");
        }

        TSubscrFans buyerFan = subscrFansDao.selectByPrimaryKey(params.getOpenId());
        TTaokeActiveOrder order = new TTaokeActiveOrder();
        order.setCommision(active.getCommision());
        order.setBackmoney(active.getBackmoney());

        if (buyerFan == null || buyerFan.getSubstate() == 1) {
            throw new H5SubscrException();
        }
        if(buyerFan.getState() == 1){
            throw new BusinessException("请联系公众号管理员");
        }
        if (active.getFanslimit() != null && active.getFanslimit() == 1) {
            if (buyerFan.getMobile() == null) {
                throw new H5MobileException();
            }
        }
        if (active.getOpenorderlimit() == 0) {//不允许重复下单()--返利为0,佣金也是0
            Long count = taokeActiveOrderDao.selectIdByOpenId(params.getOpenId(), params.getActiveId());
            if(count != null && count > 0){
                order.setCommision(0);
                order.setBackmoney(0);
            }
        }
        if (active.getOpenselfcommision() == 0) {//自己下单不会获取佣金(),但是会获取返利
            if (params.getOpenId().equals(params.getTaokeOpenId())) {
                order.setCommision(0);
            }
        }

        TSubscrFans taokeFan = subscrFansDao.selectByPrimaryKey(params.getTaokeOpenId());
        order.setTitle(active.getTitle());
        order.setBuyeropenid(buyerFan.getOpenid());
        order.setBuyername(buyerFan.getNickname());
        order.setBuyerface(buyerFan.getHeadimgurl());
        order.setBuyermobile(buyerFan.getMobile());
        order.setTaokeopenid(taokeFan.getOpenid());
        order.setTaokename(taokeFan.getNickname());
        order.setTaokeface(taokeFan.getHeadimgurl());
        order.setTaokemobile(taokeFan.getMobile());
        order.setTotalnum(1);
        order.setTotalprice(active.getPrice());

        order.setActiveid(active.getId());
        order.setShopid(active.getShopid() + "");

        order.setAppid(params.getAppId());
        order.setYoukeid(dyk);
        order.setState(0);
        order.setCreatetime(new Date());
        taokeActiveOrderDao.insertSelective(order);

        TTaokeActive model = new TTaokeActive();
        model.setId(active.getId());
        model.setNum(active.getNum() - 1);
        if (model.getNum() == 0) {
            model.setState(2);
        }
        taokeActiveDao.updateByPrimaryKeySelective(model);

        Map<String, String> msgMap = SubOrderMessageConstant.TYPE.get(SubOrderMessageConstant.TAO_KE);
        if(order.getCommision() > 0 || order.getBackmoney() > 0){
            //推送消息,提供填写订单号入口
            NewsMsgMessage newsMsgMessage = new NewsMsgMessage();
            newsMsgMessage.setAppId(params.getAppId());
            newsMsgMessage.setDescription(SubOrderMessageConstant.DESCRIPTION.replace("{TITLE}", active.getTitle()));
            newsMsgMessage.setOpenId(params.getOpenId());
            newsMsgMessage.setPicUrl(active.getCover());
            newsMsgMessage.setTitle(msgMap.get("title"));
            newsMsgMessage.setUrl(msgMap.get("url").replace("{APPID}", params.getAppId()));
            queueSender.send("newsmsg.queue", newsMsgMessage);
        }
        return order.getId();
    }

    @Override
    public PageInfo<H5TaokeOrderDetailQueryRetVo> queryDetailList(H5TaokeDetailQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<H5TaokeOrderDetailQueryRetVo> detailList = taokeActiveOrderDao.queryListByTaokeOpenId(params.getTaokeOpenId(), params.getActiveId());
        return new PageInfo<>(detailList);
    }

    @Override
    public void saveOrderNum(String appId, String openId, Long orderId, String orderNo) {

        if (orderId == null || orderNo == null) {
            throw new BusinessException("请传入订单号或者订单Id");
        }
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

        Map<String, Object> detail = taokeActiveOrderDao.selectOrderDetail(orderId);
        if(detail == null){
            throw new BusinessException("不存在此订单,无效的订单号");
        }
        String goodId = (String)detail.get("goodsId");
        Integer orderState = (Integer) detail.get("state");
        Integer commision = (Integer)detail.get("commision");
        Integer openBackLimit = (Integer)detail.get("openBackLimit");
        Integer fansLimit = (Integer) detail.get("fansLimit");
        String buyerOpenId = (String)detail.get("buyerOpenId");
        if(buyerOpenId == null || !buyerOpenId.equals(openId)){
            throw new BusinessException("参与者和提交订单号者不同");
        }

        if(subscrFans.getSubstate() == 1){
            throw new H5SubscrException();
        }
        if(fansLimit != null && fansLimit == 1 && subscrFans.getMobile() == null){
            throw new H5MobileException();
        }

        Integer count = taokeActiveOrderDao.selectCount(orderNo, dyk);
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

        TTaokeActiveOrder order = new TTaokeActiveOrder();
        if (openBackLimit == 1) {//一个订单号可以享受多次优惠
            order.setCommision(shopOrder.getNum() * commision );
        }
        order.setTotalnum(shopOrder.getNum());
        order.setTotalprice(shopOrder.getTotalprice());
        order.setId(orderId);
        order.setOrderno(orderNo);
        order.setOrdertime(new Date());
        order.setState(2);
        taokeActiveOrderDao.updateByPrimaryKeySelective(order);
    }

    @Override
    public void updateState(Integer activeId, String youkeId, Integer state) {
        if(activeId != null){
            youke.common.model.vo.result.TaokeActiveVo info = taokeActiveDao.selectDetail(activeId, youkeId);
            if(info != null){
                Integer state1 = info.getState();
                if(state == 1){
                    if(state1 != 0){
                        throw new BusinessException("["+ info.getTitle() +"] 活动处于非待开始 手动上线失败");
                    }else{
                        TTaokeActive active = new TTaokeActive();
                        active.setId(info.getId());
                        active.setState(state);
                        active.setStarttime(new Date());
                        taokeActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
                if(state == 2){
                    if(state1 != 1){
                        throw new BusinessException("["+ info.getTitle() +"] 活动处于非上线状态 手动下线失败");
                    }else{
                        TTaokeActive active = new TTaokeActive();
                        active.setId(info.getId());
                        active.setState(state);
                        taokeActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
            }
        }
    }
    
    @Override
    public String createPoter(Integer activeId, String openId) {
        if (activeId < 0) {
            throw new BusinessException("传入正确的活动id");
        }
        if (!StringUtil.hasLength(openId)) {
            throw new BusinessException("传入正确的活动openId");
        }
        TTaokeActive active = taokeActiveDao.selectByPrimaryKey(activeId);
        TSubscrFans wxFans = subscrFansDao.selectByPrimaryKey(openId);
        if (active.getFanslimit() == 1) {
            subscrFansDao.selectByPrimaryKey(openId);
            if (wxFans == null) {
                throw new H5SubscrException();
            }
            if (wxFans.getMobile() == null) {
                throw new H5MobileException();
            }
        }
        if (active.getBackbg() == null) {
            throw new BusinessException("海报背景图片 不能为空");
        }

        //存入活动时间
        String taokeTime = (String)RedisSlaveUtil.get("taoke_" + openId + "_" + activeId);
        if(taokeTime == null){
            taokeTime = DateUtil.formatDateTime(new Date());
            RedisUtil.set("taoke_" + openId + "_" + activeId, taokeTime);
        }

        String h5_taoke_page = configDao.selectVal("h5_taoke_page");
        String url = h5_taoke_page.replace("{appId}", wxFans.getAppid()) + "&openId=" + openId + "&id=" + activeId;
        InputStream posterStream = HttpConnUtil.getStreamFromNetByUrl(active.getBackbg() + "?x-oss-process=image/resize,m_fill,h_600,w_600");
        InputStream fingerStream = HttpConnUtil.getStreamFromNetByUrl(ApiCodeConstant.OSS_BASE + "png/add94cd21712e55836a6aba74f8a3ac6.png");
        InputStream codeStream = HttpConnUtil.getStreamFromNetByUrl(ApiCodeConstant.DOMAIN_PCAPI + "common/qrcode?d=" + URLEncoder.encode(url));
        InputStream headImgInput = HttpConnUtil.getStreamFromNetByUrl(wxFans.getHeadimgurl());
        ;
        File resultFile = null;
        try {
            resultFile = File.createTempFile(UUID.randomUUID().toString(), ".jpg");
            PoterUtils.graphicsGeneration(active.getSlogan(), DateUtil.formatDateTime(new Date()), headImgInput, posterStream, fingerStream, codeStream, resultFile);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        } finally {
            if (posterStream != null) {
                try {
                    posterStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fingerStream != null) {
                try {
                    fingerStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (codeStream != null) {
                try {
                    codeStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (headImgInput != null) {
                try {
                    headImgInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String key = "haibao/" + UUID.randomUUID().toString() + ".jpg";
        return FileUpOrDwUtil.uploadLocalFile(resultFile, key);
    }

    @Override
    public H5TaokeOrderQueryRetVo getOrderInfo(String appId, String openId, long orderId) {
        if(!StringUtil.hasLength(appId)
                || !StringUtil.hasLength(openId)){
            return null;
        }
        H5TaokeOrderQueryRetVo info = taokeActiveOrderDao.selectOrderInfo(appId, openId, orderId);
        return info;
    }

    private static void doImportOrder(List<TaokeOrderQueryRetVo> orderList, OutputStream outputStream) {
        //写入临时文件
        //表头
        String[] headers = new String[]{
                "记录编号",
                "商品图片",
                "商品标题",
                "商品Id",
                "购物订单号",
                "买家微信openId",
                "买家微信头像",
                "买家微信手机",
                "买家微信昵称",
                "淘客微信openId",
                "淘客微信头像",
                "淘客微信手机",
                "淘客微信昵称",
                "下单总金额(分)",
                "购买总数量",
                "买家优惠金额",
                "淘客佣金",
                "返利时间",
                "提交时间",
                "返利状态"
        };
        String[] content = new String[20];
        try {
            CsvWriter csvWriter = new CsvWriter(outputStream, ',', Charset.forName("GBK"));
            csvWriter.writeRecord(headers);
            for (TaokeOrderQueryRetVo vo : orderList) {
                content[0] = vo.getId() + "";
                content[1] = vo.getCover();
                content[2] = vo.getTitle();
                content[3] = vo.getGoodsId();
                content[4] = vo.getOrderNo();
                content[5] = vo.getBuyerOpenId();
                content[6] = vo.getBuyerFace();
                content[7] = vo.getBuyerName();
                content[8] = vo.getBuyerMobile();
                content[9] = vo.getTaokeOpenId();
                content[10] = vo.getTaokeFace();
                content[11] = vo.getTaokeMobile();
                content[12] = vo.getTaokeName();
                content[13] = vo.getTotalPrice() + "";
                content[14] = vo.getTotalNum() + "";
                content[15] = vo.getBackMoney() + "";
                content[16] = vo.getCommision() + "";
                content[17] = vo.getBackTime() != null ? DateUtil.formatDate(vo.getBackTime()) : null;
                content[18] = vo.getCreateTime() != null ? DateUtil.formatDate(vo.getCreateTime()) : null;
                content[19] = vo.getStateDisplay();
                csvWriter.writeRecord(content);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
