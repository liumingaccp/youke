package youke.order.core.service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.SecretException;
import com.taobao.api.domain.TradeRate;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderatesGetResponse;
import com.taobao.api.security.SecurityClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.order.common.base.Base;
import youke.order.common.dao.IShopDao;
import youke.order.common.dao.IShopFansDao;
import youke.order.common.dao.IShopOrderDao;
import youke.order.common.dao.IbTradeDao;
import youke.order.common.model.TShop;
import youke.order.common.model.TShopFans;
import youke.order.common.model.TShopOrder;
import youke.order.common.model.TbTrade;
import youke.order.common.util.DateUtil;
import youke.order.common.util.RedisUtil;
import youke.order.common.util.TbConstans;
import youke.order.core.mq.QueueSender;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderService extends Base {

    @Resource
    private IShopDao shopDao;
    @Resource
    private IShopFansDao shopFansDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private IbTradeDao tbTradeDao;
    @Resource
    private QueueSender queueSender;

    private Date lastDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    public Date getLastDate() {
        Date date = (Date) RedisUtil.get("SYNC-TBORDER-TIME");
        if(date==null) {
            Date newDate = new Date();
            setLastDate(newDate);
            return newDate;
        }
        return date;
    }

    public void setLastDate(Date date) {
        RedisUtil.set("SYNC-TBORDER-TIME",date);
    }

    public void updateOrder(){
        lastDate = getLastDate();
        List<String> snicks = shopDao.selectDianNames();
        if(snicks.size()>0) {
            List<TbTrade> tbTrades = tbTradeDao.selectLastTrades(snicks, lastDate);
            if (tbTrades != null && tbTrades.size() > 0) {
                System.out.println(DateUtil.getDateTime()+" 发现" + tbTrades.size() + "条最新订单");
                setLastDate(tbTrades.get(0).getModified());
                for (TbTrade tbTrade : tbTrades) {
                    System.out.println(DateUtil.getDateTime() + " 同步订单号:" + tbTrade.getTid());
                    JSONObject tradeObj = JSONObject.fromObject(tbTrade.getJdpResponse()).getJSONObject("trade_fullinfo_get_response").getJSONObject("trade");
                    saveShopOrder(tradeObj);
                    int state = TbConstans.TB_ORDER_STATUS.get(tbTrade.getStatus());
                    if (state > 0 && state < 4) {
                        TShop shop = shopDao.selectByDianName(tradeObj.getString("seller_nick"));
                        queueSender.send("taobaoorder.queue",tbTrade.getTid()+"#"+state+"#"+shop.getYoukeid());
                    }
                }
                System.out.println(DateUtil.getDateTime()+" 同步订单成功");
            }
        }
    }

    private void saveShopOrder(JSONObject tradeObj){
        JSONArray orders = tradeObj.getJSONObject("orders").getJSONArray("order");
        TShop shop = shopDao.selectByDianName(tradeObj.getString("seller_nick"));
        if(shop!=null){
            //判断是否有当前订单
            if (orders != null && orders.size() > 0) {
                SecurityClient securityClient = TbConstans.getSecurityClient();
                //父订单详情
                String receiver_city = tradeObj.getString("receiver_city");
                String receiver_address = tradeObj.getString("receiver_address");
                String receiver_zip = tradeObj.getString("receiver_zip");
                String receiver_state = tradeObj.getString("receiver_state");
                String receiver_name = tradeObj.getString("receiver_name");
                String receiver_mobile = tradeObj.getString("receiver_mobile");
                String total_payment = tradeObj.getString("payment");
                String create_time = tradeObj.getString("created");
                String pay_time = null;
                if(tradeObj.containsKey("pay_time"))
                    pay_time =tradeObj.getString("pay_time");
                String end_time = null;
                if(tradeObj.containsKey("end_time"))
                    end_time =tradeObj.getString("end_time");
                String buyer_email = ""; //买家邮箱
                String buyer_nick = tradeObj.getString("buyer_nick");//买家昵称
                int status = TbConstans.TB_ORDER_STATUS.get(tradeObj.getString("status"));
                try {
                    receiver_mobile = securityClient.decrypt(receiver_mobile, "phone", shop.getAccesstoken());
                    receiver_name = securityClient.decrypt(receiver_name, "receiver_name", shop.getAccesstoken());
                    buyer_nick = securityClient.decrypt(buyer_nick, "nick", shop.getAccesstoken());
                } catch (SecretException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < orders.size(); i++) {
                    JSONObject order = orders.getJSONObject(i);
                    long oid = order.getLong("oid");
                    long num_iid = order.getLong("num_iid");
                    String payment = order.getString("payment");
                    //获取订单买家评价状态
                    int buyerRate = 1; //暂时默认好评
//                    int buyerRate = 0;
                    //如果已经评价,则获取评价结果
//                    if (order.getBoolean("buyer_rate")) {
//                        buyerRate = getTradeRates(oid, shop.getAccesstoken());
//                    }
                    //如果存在此订单,只是修改订单状态和时间..
                    TShopOrder shopOrder = shopOrderDao.selectByOrderno(oid+"");
                    if (shopOrder != null) {
                        if (status != shopOrder.getState()) {
                            shopOrder.setState(status);
                            if (notEmpty(end_time))
                                shopOrder.setEndtime(DateUtil.parseDate(end_time));
                            if (notEmpty(pay_time))
                                shopOrder.setPaytime(DateUtil.parseDate(pay_time));
                            shopOrder.setBuyerrate(buyerRate);
                            shopOrder.setTotalprice((int) (Double.parseDouble(payment) * 100));
                            shopOrderDao.updateByPrimaryKeySelective(shopOrder);
                        }
                    } else {
                        shopOrder = new TShopOrder();
                        String title = order.getString("title");
                        String pic_path = "";
                        if(order.containsKey("pic_path"))
                           pic_path = order.getString("pic_path");
                        int num = order.getInt("num");              //数量
                        String price = order.getString("price");    //单价
                        shopOrder.setOrderno(oid + "");
                        shopOrder.setTitle(title);
                        shopOrder.setPicpath(pic_path);
                        shopOrder.setShopid(shop.getId());
                        shopOrder.setShoptype(1);
                        shopOrder.setNum(num);
                        shopOrder.setPrice((int) (Double.parseDouble(price) * 100));
                        shopOrder.setTotalprice((int) (Double.parseDouble(payment) * 100));
                        shopOrder.setPaytime(DateUtil.parseDate(pay_time));
                        shopOrder.setReceiveaddress(receiver_address);
                        shopOrder.setReceiverzip(receiver_zip);
                        shopOrder.setReceivestate(receiver_state);
                        shopOrder.setReceivename(receiver_name);
                        shopOrder.setReceivemobile(receiver_mobile);
                        shopOrder.setBuyerrate(buyerRate);
                        shopOrder.setState(status);
                        shopOrder.setGoodid(num_iid + "");
                        shopOrder.setBuyername(buyer_nick);
                        shopOrder.setEndtime(DateUtil.parseDate(end_time));
                        shopOrder.setYoukeid(shop.getYoukeid());
                        shopOrderDao.insertSelective(shopOrder);
                    }
                }
                //交易成功后保存粉丝
                if (status == 4) {
                    Map dealMap = shopOrderDao.selectDealStat(buyer_nick,shop.getId());
                    TShopFans shopFans = new TShopFans();
                    shopFans.setNickname(buyer_nick);
                    shopFans.setAvgdealtotal(Double.parseDouble(dealMap.get("avgDealTotal").toString()));
                    shopFans.setDealnum(Integer.parseInt(dealMap.get("dealNum").toString()));
                    shopFans.setDealtotal(Double.parseDouble(dealMap.get("dealTotal").toString()));
                    shopFans.setLasttime(DateUtil.parseDate(end_time));
                    shopFans.setCity(receiver_city);
                    shopFans.setMobile(receiver_mobile);
                    shopFans.setProvince(receiver_state);
                    shopFans.setTruename(receiver_name);
                    shopFans.setComefrom(0);
                    shopFans.setCountry("中国");
                    shopFans.setExperience(0);
                    shopFans.setIntegral(0);
                    shopFans.setLoginCount(1);
                    shopFans.setRegtime(DateUtil.parseDate(create_time));
                    shopFans.setSex(0);
                    shopFans.setShopid(shop.getId());
                    shopFans.setState(0);
                    shopFans.setYoukeid(shop.getYoukeid());
                    shopFans.setEmail(buyer_email);
                    saveShopFans(shopFans);
                }
            }
        }
    }

    private void saveShopFans(TShopFans shopFans){
        int count = shopFansDao.selectCountByMobileAndShopId(shopFans.getMobile(),shopFans.getShopid());
        if(count>0){
            shopFansDao.updateByPrimaryKeySelective(shopFans);
        }else{
            shopFansDao.insertSelective(shopFans);
        }
    }

    private int getTradeRates(long oid, String token) {
        DefaultTaobaoClient client = TbConstans.getDefaultTaobaoClient();
        TraderatesGetRequest req = new TraderatesGetRequest();
        req.setFields("tid,result,created,item_title");
        req.setRateType("get");
        req.setRole("buyer");
        req.setPageNo(1L);
        req.setPageSize(1L);//只查询最新一条
        req.setTid(oid);
        req.setUseHasNext(true);
        try {
            TraderatesGetResponse rsp = client.execute(req, token);
            List<TradeRate> tradeRates = rsp.getTradeRates();
            if(tradeRates != null && tradeRates.size() > 0){
                TradeRate tradeRate = tradeRates.get(0);
                String result =  tradeRate.getResult();
                if(result!=null) {
                    return TbConstans.TB_RTADE_RATE.get(result);
                }
            }
        } catch (ApiException e){
            e.printStackTrace();
        }
        return 0;
    }

}
