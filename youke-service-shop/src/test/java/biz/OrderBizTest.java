package biz;

import org.junit.Test;
import youke.common.dao.IShopOrderDao;
import youke.common.model.TShopOrder;
import youke.common.utils.DateUtil;
import youke.common.utils.IDUtil;

import javax.annotation.Resource;
import java.util.Date;

public class OrderBizTest extends BaseJunit4Test {

    @Resource
    private IShopOrderDao shopOrderDao;


    @Test
    public void doOrder(){
        //dykw1wF3Xy   店有客   shopId  = 11
        //dykb7mhlUa   体验号   shopId  = 13

        int max = 100;
        String dykTitle = "店有客";
        String title =dykTitle+"体验商品";
        String youkeId = "dykw1wF3Xy";
        int shopId = 11;
        String orderPix = "DYK";
        Date date = new Date();
        long curss = date.getTime()/1000;
        System.out.println("----------"+dykTitle+"订单-----------");
        for(int i=0;i<max;i++){
            String iStr = i<10?"0"+i:i+"";
            TShopOrder order = new TShopOrder();
            order.setBuyername("刘有客");
            order.setGoodid(curss+iStr);
            order.setTotalprice(10000);
            order.setPaytime(new Date());
            order.setEndtime(new Date());
            order.setState(4);
            order.setBuyerrate(1);
            order.setNum(1);
            order.setOrderno(orderPix+ IDUtil.getRandomId());
            order.setPicpath("https://www.dianyk.com/images/logo.png");
            order.setPrice(10000);
            order.setShopid(shopId);
            order.setShoptype(1);
            order.setTitle(title+(i+1));
            order.setYoukeid(youkeId);
            shopOrderDao.insertSelective(order);
            System.out.println(order.getOrderno());
        }
    }


}