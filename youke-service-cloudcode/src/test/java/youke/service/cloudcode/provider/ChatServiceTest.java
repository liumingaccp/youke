package youke.service.cloudcode.provider;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import youke.facade.cloudcode.provider.IChatService;
import youke.facade.cloudcode.provider.ICloudCodeService;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class ChatServiceTest extends BaseJunit4Test {

    @Resource
    private ICloudCodeService cloudCodeService;


    @Test
    public void getChatMessage() {


        System.out.println(cloudCodeService.getCloudCode(1L,"sssssssss"));
    }
}