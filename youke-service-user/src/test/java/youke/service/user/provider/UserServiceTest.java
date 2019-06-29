package youke.service.user.provider;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import youke.facade.user.provider.IUserService;
import youke.service.user.biz.IUserBiz;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class UserServiceTest extends BaseJunit4Test {

    @Resource
    private IUserBiz userBiz;
    @Resource
    private IUserService userService;

    @Test
    public void getInfo() {
        System.out.println(JSONArray.fromObject(userService.getVipFuncs(3)));
    }

}