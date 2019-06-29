package youke.service.fans.provider;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import youke.common.dao.IFriendShopfansDao;
import youke.common.model.TFriendShopfans;
import youke.common.model.TTag;
import youke.facade.fans.provider.IFansTagService;
import youke.service.fans.biz.IFansShopBiz;

import javax.annotation.Resource;
import java.util.Date;

public class FansServiceTest extends BaseJunit4Test {

    @Resource
    private IFansTagService fansTagService;

    @Resource
    private IFansShopBiz fansShopBiz;

    @Test
    public void doTest(){
        //System.out.println(JSONArray.fromObject(fansTagService.getList("dykRv3GWd8")).toString());

        fansTagService.delTag(490,"dykb7Yhmay","wx913f4c4d60d9f820");

    }


}