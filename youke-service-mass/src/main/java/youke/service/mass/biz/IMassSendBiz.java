package youke.service.mass.biz;

import youke.facade.mass.vo.MassSMSMessage;

public interface IMassSendBiz {
    /**
     * 创建模板,发送短信
     */
    void doMass(MassSMSMessage message);
}
