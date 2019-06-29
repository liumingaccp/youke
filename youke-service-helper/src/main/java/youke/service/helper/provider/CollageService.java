package youke.service.helper.provider;

import com.csvreader.CsvWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.*;
import net.sf.json.util.JSONStringer;
import net.sf.json.util.JSONUtils;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.exception.H5MobileException;
import youke.common.exception.H5SubscrException;
import youke.common.model.*;
import youke.common.model.vo.param.helper.CollageOrderQueryVo;
import youke.common.model.vo.param.helper.CollageQueryVo;
import youke.common.model.vo.param.helper.TuanVo;
import youke.common.model.vo.result.helper.*;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.IDUtil;
import youke.common.utils.StringUtil;
import youke.facade.helper.provider.ICollageService;
import youke.facade.helper.vo.CollageActiveVo;
import youke.facade.wx.provider.IWeixinMessageService;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/12
 * Time: 9:55
 */
@Service
public class CollageService extends Base implements ICollageService {

    @Resource
    private ICollageActiveDao collageActiveDao;
    @Resource
    private ICollageActiveOrderDao collageActiveOrderDao;
    @Resource
    private ICollageActiveTuanDao collageActiveTuanDao;
    @Resource
    private IConfigDao configDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IWeixinMessageService weixinMessageService;
    @Resource
    private IShopDao shopDao;

    @Override
    public int addActive(CollageActiveVo params) {
        TCollageActive active = new TCollageActive();

        if (null == params.getStartTime() || null == params.getEndTime()) {
            throw new BusinessException("任务开始或者结束时间不能为空");
        }
        active.setStarttime(DateUtil.parseDate(params.getStartTime()));
        active.setEndtime(DateUtil.parseDate(params.getEndTime()));
        Date today = DateUtil.parseDate(DateUtil.todayStartDate());
        if (active.getStarttime().getTime() < today.getTime()) {
            throw new BusinessException("开始时间不能小于当前时间");
        }
        if (active.getStarttime().getTime() > active.getEndtime().getTime()) {
            throw new BusinessException("结束时间要大于开始时间");
        }
        String coverPics = params.getCoverPics();
        String[] coverArray = null;
        try {
            coverArray = coverPics.split(",");

        } catch (Exception e) {
            throw new BusinessException("请传入合法的图片参数");
        }
        if (coverArray == null || coverArray.length <= 0) {
            throw new BusinessException("请传入封面图片");
        } else {
            active.setCoverpics(coverPics);
            active.setCover(coverArray[0]);
        }


        Integer shopId = params.getShopId();
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

        if(params.getPrice() == null || params.getPrice() <= 0){
            throw new BusinessException("价格不能小于0");
        }
        if(params.getTotalNum() == null || params.getTotalNum() <= 0){
            throw new BusinessException("数量不能小于0");
        }
        if(params.getTuanFansNum() == null || params.getTuanFansNum() <= 0){
            throw new BusinessException("团粉丝不能小于0");
        }
        if(params.getTuanPrice() == null || params.getTuanPrice() <= 0){
            throw new BusinessException("团价格不能小于0");
        }

        active.setId(params.getId());
        active.setTitle(params.getTitle());
        active.setShopid(params.getShopId());
        active.setGoodsid(params.getGoodsId());
        active.setGoodsurl(params.getGoodsUrl());
        active.setContent(params.getContent());
        active.setIntro(JSONUtils.valueToString(params.getIntro()));
        active.setPrice(params.getPrice());
        active.setTotalnum(params.getTotalNum());
        active.setNum(params.getTotalNum());
        active.setTuanprice(params.getTuanPrice());
        active.setTuanfansnum(params.getTuanFansNum());
        active.setTuanhour(params.getTuanHour());
        active.setFanslimit(params.getFansLimit());
        active.setWaitday(params.getWayDay());

        active.setAppid(params.getAppId());
        active.setYoukeid(params.getYoukeId());

        //检测商家是否开通微信支付
        int payState = subscrConfigDao.selectPayState(params.getAppId());
        if(payState == 0){
            throw new BusinessException("尚未设置绑定微信支付");
        }

        Integer id = active.getId();
        if (id == 0) {
            active.setState(0);
            active.setCreatetime(new Date());
            collageActiveDao.insertSelective(active);
        }

        if (id > 0) {
            TCollageActive oldActive = collageActiveDao.selectByPrimaryKey(id);
            if (oldActive == null) {
                throw new BusinessException("不存在此活动,请提交正确的参数");
            }
            if (oldActive.getState() != 0) {
                throw new BusinessException("该活动已开启,不支持修改");
            }
            active.setCreatetime(oldActive.getCreatetime());
            collageActiveDao.updateByPrimaryKeySelective(active);
        }

        return active.getId();
    }

    @Override
    public PageInfo<CollageQueryRetVo> queryList(CollageQueryVo params, String codePath) {
        PageHelper.startPage(params.getPage(), params.getLimit(), "collage.createTime desc");
        List<CollageQueryRetVo> list = collageActiveDao.queryList(params);
        if (list != null && list.size() > 0) {
            for (CollageQueryRetVo vo : list) {
                String h5_collage_page = configDao.selectVal("h5_collage_page");
                String preUrl = h5_collage_page.replace("{appId}", params.getAppId()) + "&id=" + vo.getId();
                vo.setPreUrl(preUrl);
                vo.setPreCodeUrl(codePath + URLEncoder.encode(preUrl));
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<CollageOrderQueryRetVo> queryOrderList(CollageOrderQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit(), "torder.joinTime desc");
        List<CollageOrderQueryRetVo> list = collageActiveOrderDao.queryList(params);
        return new PageInfo<>(list);
    }

    @Override
    public String doImportOrder(CollageOrderQueryVo params) {
        PageInfo<CollageOrderQueryRetVo> info = this.queryOrderList(params);
        List<CollageOrderQueryRetVo> list = info.getList();
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
    public void deleteActive(Integer activeId) {
        if (activeId != null && activeId > 0) {
            TCollageActive active = collageActiveDao.selectByPrimaryKey(activeId);
            if (active == null) {
                throw new BusinessException("不存在" + activeId + "对应的活动");
            }
            Integer state = active.getState();
            if (state == 0) {
                collageActiveDao.deleteByPrimaryKey(activeId);
            } else if (state == 1 || state == 2) {
                TCollageActive updateActive = new TCollageActive();
                updateActive.setId(activeId);
                updateActive.setState(3);
                collageActiveDao.updateByPrimaryKeySelective(updateActive);
            }
        }
    }

    @Override
    public void updateState(Integer activeId, String appId, String dykId, Integer state) {
        if (activeId != null) {
            TCollageActive info = collageActiveDao.selectByPrimaryKeyAndDyk(activeId, appId, dykId);
            if (info != null) {
                Integer state1 = info.getState();
                if (state == 1) {
                    if (state1 != 0) {
                        throw new BusinessException("[" + info.getTitle() + "] 活动处于非待开始 手动上线失败");
                    } else {
                        TCollageActive active = new TCollageActive();
                        active.setId(info.getId());
                        active.setState(state);
                        active.setStarttime(new Date());
                        collageActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
                if (state == 2) {
                    if (state1 != 1) {
                        throw new BusinessException("[" + info.getTitle() + "] 活动处于非上线状态 手动下线失败");
                    } else {
                        TCollageActive active = new TCollageActive();
                        active.setId(info.getId());
                        active.setState(state);
                        collageActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
            }else {
                throw new BusinessException("活动不存在,请检查参数");
            }
        }
    }

    @Override
    public CollageActiveDetailVo getAtiveDetail(Integer id, String appId, String dykId) {
        if (id == null || id < 0) {
            return null;
        }
        TCollageActive active = collageActiveDao.selectByPrimaryKey(id);
        if (active == null) {
            return null;
        } else {
            CollageActiveDetailVo detail = new CollageActiveDetailVo();
            detail.setId(active.getId());
            detail.setShopId(active.getShopid());
            detail.setGoodsUrl(active.getGoodsurl());
            detail.setCoverPics(active.getCoverpics());
            detail.setTitle(active.getTitle());
            detail.setContent(active.getContent());
            detail.setStartTime(active.getStarttime());
            detail.setEndTime(active.getEndtime());
            if(active.getIntro() != null && active.getIntro().startsWith("[")){
                detail.setIntro(JSONArray.fromObject(active.getIntro()));
            }
            detail.setPrice(active.getPrice());
            detail.setTotalNum(active.getTotalnum());
            detail.setTuanPrice(active.getTuanprice());
            detail.setTuanFansNum(active.getTuanfansnum());
            detail.setTuanHour(active.getTuanhour());
            detail.setFansLimit(active.getFanslimit());
            detail.setWaitDay(active.getWaitday());

            return detail;
        }
    }

    @Override
    public PageInfo<CollageQueryRetVoH5> queryListForH5(CollageQueryVo params) {
        if(params == null){
            return new PageInfo<>(null);
        }
        String appId = params.getAppId();
        if(!StringUtil.hasLength(appId)){
            throw new BusinessException("参数异常");
        }
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<CollageQueryRetVoH5> list = collageActiveDao.queryListForH5(params);
        if(list != null && list.size() > 0){
            for(CollageQueryRetVoH5 vo : list){
                vo.setUsedNum(IDUtil.getGrowId(vo.getId()));
            }
        }
        return new PageInfo<>(list);

    }

    @Override
    public PageInfo<CollageQueryRetVoByOpenId> queryListForOpenId(CollageQueryVo params) {
        if(params == null){
            return new PageInfo<>(null);
        }
        if(params.getAppId() == null && params.getOpenId() == null){
            throw new BusinessException("参数异常");
        }
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<CollageQueryRetVoByOpenId> list = collageActiveDao.queryListForOpenId(params);
        if(list != null && list.size() > 0){
            for(CollageQueryRetVoByOpenId vo : list){
                Date date = null;
                int i = vo.getEndTime().indexOf(".");
                if(i>0){
                    date = DateUtil.parseDate(vo.getEndTime().substring(0, i));
                }else{
                    date = DateUtil.parseDate(vo.getEndTime());
                }
                if(date != null){
                    vo.setEnd(date.getTime());
                }
            }
        }
        return new PageInfo<>(list);

    }

    @Override
    public TuanDetailVo getTuanDetailByOpenId(String appId, String openId, Integer tuanId) {
        if(!StringUtil.hasLength(appId)
                || !StringUtil.hasLength(openId)
                || tuanId == null || tuanId <0 ){
            throw new BusinessException("参数异常");
        }

        TuanDetailVo detail = collageActiveTuanDao.getTuanDetailByOpenId(appId, openId, tuanId);
        if(detail != null){
            String goodsurl = detail.getGoodsUrl();
            if(goodsurl.contains("taobao.com")){
                detail.setShopType(0);
            }else if(goodsurl.contains("tmall.com")){
                detail.setShopType(1);
            } else if(goodsurl.contains("jd.com")){
                detail.setShopType(2);
            }
            if(detail.getIntros() != null && detail.getIntros().startsWith("[")){
                detail.setIntro(JSONArray.fromObject(detail.getIntros()));
            }

            Date date = null;
            int i = detail.getEndTime().indexOf(".");
            if(i>0){
                date = DateUtil.parseDate(detail.getEndTime().substring(0, i));
            }else{
                date = DateUtil.parseDate(detail.getEndTime());
            }
            if(date != null){
                detail.setEnd(date.getTime());
            }

            //根据openId查询查询订单号
            TCollageActiveOrder torder = collageActiveOrderDao.selectOrderByOpenIdAndTuanId(openId, tuanId);
            if(torder == null){
                detail.setOrderNo(null);
            }else {
                detail.setOrderNo(torder.getOrderno());
                detail.setState(torder.getState());
            }

            //查询更多的参与者
            detail.setUsedNum(IDUtil.getGrowId(detail.getId()));
            List<WxFansLessVo> wxFansVos = collageActiveOrderDao.getParticipantByTuanId(appId, tuanId);
            if(wxFansVos != null && wxFansVos.size() > 0){
                List<WxFansLessVo> orderFans = detail.getOrderFans();
                WxFansLessVo first = new WxFansLessVo();
                first.setOpenId(detail.getOpenId());
                first.setWxFace(detail.getWxFace());
                first.setWxName(detail.getWxName());
                orderFans.add(first);
                for(WxFansLessVo vo : wxFansVos){
                    if(vo !=null && !vo.getOpenId().equals(detail.getOpenId())){
                        orderFans.add(vo);
                    }
                }
                detail.setLeftTuanNum(orderFans.size());
            }
        }
        return detail;
    }

    public CollageActiveDetailAndTuanVo getActiveDetailWithTuanDetail(String appId, String openId, Integer activeId){
        if(!StringUtil.hasLength(appId)
                || activeId == null || activeId <0 ){
            throw new BusinessException("参数异常");
        }
        CollageActiveDetailAndTuanVo vo = collageActiveDao.selectActiveDetail(appId, activeId);
        if(vo != null){
            String goodsurl = vo.getGoodsUrl();
            if(goodsurl.contains("taobao.com")){
                vo.setShopType(0);
            }else if(goodsurl.contains("tmall.com")){
                vo.setShopType(1);
            } else if(goodsurl.contains("jd.com")){
                vo.setShopType(2);
            }

            if(vo.getIntros() != null && vo.getIntros().startsWith("[")){
                vo.setIntro(JSONArray.fromObject(vo.getIntros()));
            }

            vo.setUsedNum(IDUtil.getGrowId(activeId));

            List<CollageTuanItem> tuanItems = collageActiveTuanDao.selectByActiveId(appId, activeId);
            Integer count = collageActiveOrderDao.queryCountByActiveId(appId, activeId);
            vo.setTuanNum(count);
            vo.setTuanItems(tuanItems);
        }
        return vo;
    }

    @Override
    public CollageOrderDetailRetVo getExamineDetail(Long orderId,String appId, String dykId) {
        if (empty(orderId) && empty(dykId)) {
            throw new BusinessException("无效的请求或参数");
        }
        TCollageActiveOrder order = collageActiveOrderDao.selectByPrimaryKey(orderId);
        CollageOrderDetailRetVo detail;
        if (order != null) {
            if (order.getOrderno() != null) {
                TShopOrder shopOrder = shopOrderDao.selectByOrderno(order.getOrderno());
                if (shopOrder != null) {
                    detail = new CollageOrderDetailRetVo();
                    detail.setOrderno(shopOrder.getOrderno());
                    detail.setNum(shopOrder.getNum());
                    detail.setOrderState(shopOrder.getState());
                    detail.setPicPath(shopOrder.getPicpath());
                    detail.setPrice(shopOrder.getPrice());
                    detail.setReceiveAddress(shopOrder.getReceiveaddress());
                    detail.setReceiveName(shopOrder.getReceivename());
                    detail.setTitle(shopOrder.getTitle());
                    detail.setTotalPrice(shopOrder.getTotalprice());
                    return detail;
                }
            }
        }
        return null;
    }

    public Long submitTuan(TuanVo vo){
        TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(vo.getOpenId());
        TCollageActive active = collageActiveDao.selectByPrimaryKey(vo.getActiveId());
        TShopOrder shopOrder = shopOrderDao.selectByOrderno(vo.getOrderNo());
        Integer orderId = collageActiveOrderDao.selectByOrderNo(vo.getOrderNo(), vo.getAppId());

        if(active == null){
            throw new BusinessException("不存在此活动");
        }
        if(shopOrder == null){
            throw new BusinessException("无效的订单号");
        }
        if(!shopOrder.getGoodid().equals(active.getGoodsid())){
            throw new BusinessException("订单号对应商品与活动商品不符");
        }
        if(orderId != null && orderId > 0){
            throw new BusinessException("改订单号已用于拼团活动,请勿重复使用");
        }
        if(subscrFans == null || (subscrFans.getSubstate() !=null && subscrFans.getSubstate() == 1)){
            throw new H5SubscrException();
        }
        if(active.getFanslimit() == 1){
            if(!StringUtil.hasLength(subscrFans.getMobile())){
                throw new H5MobileException();
            }
        }

        TCollageActiveOrder order = new TCollageActiveOrder();
        order.setState(0);

        TCollageActiveTuan newTuan = null;
        TCollageActiveTuan model = null;

        if(vo.getTuanId() < 0){//发起拼团
            if(active.getState() != 1){
                throw new BusinessException("该活动未开启或已经结束,请重新选择活动");
            }
            if(active.getEndtime().getTime() < new Date().getTime()){
                throw new BusinessException("已经结束,请选择其他活动");
            }
            if(active.getNum() < 1 || active.getState() == 2){
                throw new BusinessException("该活动已结束");
            }
            newTuan = new TCollageActiveTuan();
            newTuan.setState(0);
            if(active.getTuanfansnum() == 1){
                newTuan.setState(1);
                order.setState(1);
            }
            newTuan.setAlreadytuannum(1);
            newTuan.setActiveid(active.getId());
            newTuan.setCreatetime(new Date());
            newTuan.setEndtime(DateUtil.addHours(new Date(), active.getTuanhour()));
            newTuan.setOpenid(vo.getOpenId());
            newTuan.setAppid(active.getAppid());
            newTuan.setYoukeid(active.getYoukeid());

            collageActiveTuanDao.insertSelective(newTuan);
            order.setTuanid(newTuan.getId());

        }else{//参与拼团
            TCollageActiveTuan tuan = collageActiveTuanDao.selectByPrimaryKey(vo.getTuanId());
            if(tuan == null){
                throw new BusinessException("不存在此拼团,检查参数");
            }
            if(tuan.getState() == 1){
                throw new BusinessException("拼团已结束");
            }
            //查询是否参与过该拼团
            Integer id = collageActiveOrderDao.selectByOpenIdAndTuanId(vo.getOpenId(), vo.getTuanId());
            if(id != null && id > 0){
                throw new BusinessException("您已参与过该拼团");
            }
            model = new TCollageActiveTuan();
            Integer alreadytuannum = tuan.getAlreadytuannum();
            if(alreadytuannum == null){
                throw new BusinessException("拼团错误");
            }
            model.setAlreadytuannum(alreadytuannum + 1);
            model.setId(tuan.getId());
            model.setState(0);
            if(model.getAlreadytuannum() == active.getTuanfansnum()){
                model.setState(1);
                order.setState(1);
            }
            collageActiveTuanDao.updateByPrimaryKeySelective(model);
            order.setTuanid(tuan.getId());
        }

        order.setActiveid(active.getId());
        order.setAppid(active.getAppid());
        order.setYoukeid(active.getYoukeid());
        order.setTitle(active.getTitle());
        order.setWxfansname(subscrFans.getNickname());
        order.setBuyername(shopOrder.getBuyername());
        order.setPrice(active.getPrice());
        order.setTuanprice(active.getTuanprice());
        order.setOrdertime(new Date());
        order.setJointime(new Date());
        order.setBackmoney(active.getPrice() - active.getTuanprice());
        order.setActiveid(active.getId());
        order.setOpenid(vo.getOpenId());
        order.setOrderno(vo.getOrderNo());
        collageActiveOrderDao.insertSelective(order);

        //拼团活动活动数据改动
        if(active.getNum() > 1){
            active.setNum(active.getNum() - 1);
        }
        if(active.getNum() == 0 && active.getState() == 1){
            active.setState(2);
        }
        collageActiveDao.updateByPrimaryKeySelective(active);

        //如果团成功了,发送模板消息
        if(newTuan != null && newTuan.getState() != null && newTuan.getState() == 1){
            collageActiveOrderDao.updateStateByTuanId(active.getAppid(),newTuan.getId());
            weixinMessageService.sendTempPinTuan(vo.getAppId(), vo.getOpenId(), active.getTitle(), newTuan.getId());
        }

        if(model != null && model.getState() != null && model.getState() == 1){
            //查询团的所有成员
            collageActiveOrderDao.updateStateByTuanId(active.getAppid(),model.getId());
            List<String> openIds = collageActiveOrderDao.selectOpenIdByTuanId(vo.getTuanId(), vo.getAppId());
            if(openIds != null && openIds.size() > 0){
                for (String openId : openIds){
                    weixinMessageService.sendTempPinTuan(vo.getAppId(), openId, active.getTitle(), model.getId());
                }
            }
        }

        return order.getTuanid();
    }

    private static void doImportOrder(List<CollageOrderQueryRetVo> orderList, OutputStream outputStream) {
        //写入临时文件
        //表头
        String[] headers = new String[]{
                "商品名称",
                "微信昵称",
                "购物者昵称",
                "购物订单号",
                "售价 单位分",
                "拼团成交价 单位分",
                "参与时间",
                "拼团结束时间",
                "返利时间",
                "状态",
        };
        String[] content = new String[20];
        try {
            CsvWriter csvWriter = new CsvWriter(outputStream, ',', Charset.forName("GBK"));
            csvWriter.writeRecord(headers);
            for (CollageOrderQueryRetVo vo : orderList) {
                content[0] = vo.getTitle();
                content[1] = vo.getWxFansName();
                content[2] = vo.getBuyerName();
                content[3] = vo.getOrderNo();
                content[4] = vo.getPrice() + "";
                content[5] = vo.getTuanPrice() + "";
                content[6] = DateUtil.formatDate(vo.getJoinTime());
                content[7] = DateUtil.formatDate(vo.getEndTime());
                content[8] = DateUtil.formatDate(vo.getBackTime());
                content[9] = getStateDisplay(vo.getState());
                csvWriter.writeRecord(content);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getStateDisplay(Integer state) {
        //0拼团进行中, 1拼团完成, 2待收货，3待返利，4返利成功，5返利失败
        if (state == null) {
            return null;
        }
        switch (state) {
            case 0:
                return "拼团进行中";
            case 1:
                return "拼团完成";
            case 2:
                return "待收货";
            case 3:
                return "待返利";
            case 4:
                return "返利成功";
            case 5:
                return "返利失败";
            default:
                return "";
        }
    }
}
