package youke.service.mass.provider;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.exception.BusinessException;
import youke.common.model.vo.param.KeyValVo;
import youke.common.utils.StringUtil;
import youke.facade.mass.provider.IMobmsgService;
import youke.facade.mass.vo.ExpireMsgVo;
import youke.service.mass.biz.IMobmsgBiz;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MobmsgService extends Base implements IMobmsgService {

    @Resource
    private IMobmsgBiz mobmsgBiz;

    public void sendMobCode(String appId, String mobile) {
        if (notEmpty(mobile) && notEmpty(appId))
            mobmsgBiz.doSendMobCode(appId, mobile);
    }

    public boolean checkMobCode(String mobile, String code) {
        if (notEmpty(mobile) && notEmpty(code))
            return mobmsgBiz.checkMobCode(mobile, code);
        return false;
    }

    @Override
    public void sendExpireMsg(List<ExpireMsgVo> expireMsgs,int type) {
        if(notEmpty(expireMsgs))
           mobmsgBiz.sendExpireMsg(expireMsgs,type);
    }

    @Override
    public void sendYoukeSucc(List<String> mobiles) {
        if(notEmpty(mobiles))
            mobmsgBiz.sendYoukeSucc(mobiles);
    }

    @Override
    public void sendMobCodeCountMsg(List<KeyValVo> valVos){
        if(notEmpty(valVos))
            mobmsgBiz.sendMobCountMsg(valVos);
    }

    @Override
    public void sendPayFailMsg(String mobile,int money, String reason) {
        if(notEmpty(mobile)&&notEmpty(reason))
            mobmsgBiz.sendPayFailMsg(mobile, StringUtil.FenToYuan(money),reason);

    }
}
