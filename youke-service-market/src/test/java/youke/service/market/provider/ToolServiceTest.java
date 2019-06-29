package youke.service.market.provider;

import net.sf.json.JSONObject;
import org.junit.Test;
import youke.common.constants.ComeType;
import youke.common.queue.message.ActiveMassMessage;
import youke.facade.market.provider.IToolService;
import youke.service.market.biz.IGoodInfoBiz;
import youke.service.market.queue.producter.ActiveMessageProducer;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ToolServiceTest extends BaseJunit4Test {

    @Resource
    private IGoodInfoBiz  goodInfoBiz;

    @Test
    public void getProductInfo() {
        //System.out.println(JSONObject.fromObject(goodInfoBiz.getJDGoodInfo("https://item.jd.com/1345847178.html",20)).toString());
        System.out.println(JSONObject.fromObject(goodInfoBiz.getJDGoodInfo("https://item.jd.com/10467185997.html",20)).toString());

    }
}