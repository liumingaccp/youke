package youke.service.mass.biz;

import youke.common.model.vo.param.KeyValVo;
import youke.facade.mass.vo.ExpireMsgVo;

import java.util.List;

public interface IMobmsgBiz {

    void doSendMobCode(String appId, String mobile);

    boolean checkMobCode(String mobile, String code);

    void sendExpireMsg(List<ExpireMsgVo> expireMsgs, int type);

    void sendPayFailMsg(String mobile, String money, String reason);

    void sendMobCountMsg(List<KeyValVo> keyValVos);

    void sendYoukeSucc(List<String> mobiles);

    void createTemplate(String appId, String content, String label);
}
