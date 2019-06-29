package youke.facade.mass.provider;

import youke.common.model.vo.param.KeyValVo;
import youke.facade.mass.vo.ExpireMsgVo;

import java.util.List;

public interface IMobmsgService {

    /**
     * 发送验证码
     *
     * @param mobile
     */
    void sendMobCode(String appId, String mobile);

    /**
     * 检查验证码
     *
     * @param mobile
     * @param code
     * @return
     */
    boolean checkMobCode(String mobile, String code);

    /**
     * 发送账户即将过期短信
     *
     * @param expireMsgs
     * @param type       0 VIP过期，1店铺授权过期
     */
    void sendExpireMsg(List<ExpireMsgVo> expireMsgs, int type);

    /**
     * 发送店有客审核通过短信
     *
     * @param mobiles
     */
    void sendYoukeSucc(List<String> mobiles);

    /**
     * 发送短信余额不足
     * @param valVos
     */
    void sendMobCodeCountMsg(List<KeyValVo> valVos);

    /**
     * 发送支付失败短信
     *
     * @param mobile
     * @param money
     * @param reason
     */
    void sendPayFailMsg(String mobile, int money, String reason);
}
