package youke.service.market.biz.impl;

import org.junit.Test;
import youke.service.market.biz.IGoodInfoBiz;
import youke.service.market.provider.BaseJunit4Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class GoodInfoBizTest extends BaseJunit4Test {

    @Resource
    private IGoodInfoBiz goodInfoBiz;

    @Test
    public void getPDDGoodInfo() {
        goodInfoBiz.getPDDGoodInfo("https://mobile.yangkeduo.com/order_checkout.html?sku_id=50976471223&goods_id=290175743",22);


    }
}