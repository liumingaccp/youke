package youke.service.wx.biz;

import org.junit.Test;
import youke.facade.wx.provider.IWeixinMessageService;

import javax.annotation.Resource;

public class WXServiceTest extends BaseJunit4Test {

    @Resource
    private ICoreBiz coreBiz;

    @Test
    public void getInfo() {
        System.out.println(coreBiz.getShortUrl("wx913f4c4d60d9f820","aaaaaaaaaa"));
    }

}