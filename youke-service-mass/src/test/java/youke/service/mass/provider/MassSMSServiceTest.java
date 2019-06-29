package youke.service.mass.provider;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.web.bind.annotation.RestController;
import youke.common.base.Base;
import youke.common.model.vo.param.MassSMSParamVo;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.JsonUtils;
import youke.facade.mass.provider.IMassSMSService;
import youke.facade.mass.vo.MassSMSMessage;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class MassSMSServiceTest extends BaseJunit4Test {
    @Resource
    private IMassSMSService massSMSService;
    @Test
    public void callback() {
        MassSMSMessage massMessage = null;
        if (RedisSlaveUtil.hasKey("SEND_HISTORY")) {
            massMessage = (MassSMSMessage) RedisUtil.hget("SEND_HISTORY", System.currentTimeMillis()+"");
        }
        System.out.println(massMessage);
    }
    @Test
    public void send() {
       String jsonStr = "{\"mobiles\":\"18688491560\",\"filterDay\":0,\"filterWxFans\":0,\"content\":\"志伟双十一专享价，凭短信找客服领取20元优惠券，一般人我不告诉他哦~\",\"label\":\"微微一笑\",\"taskTime\":\"2018-08-02 16:08:53\",\"fileUrl\":\"\",\"fileName\":\"\",\"timestamp\":1533197297403}";
        try {
            MassSMSParamVo paramVo = JsonUtils.JsonToBean(jsonStr, MassSMSParamVo.class);
            massSMSService.saveSendTask(paramVo,"dykw1wF3Xy","wxe3e582584ba16db1");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}