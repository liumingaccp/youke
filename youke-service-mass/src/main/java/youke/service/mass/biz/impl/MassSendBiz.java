package youke.service.mass.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IMobcodeDao;
import youke.common.dao.IMobcodeRecordDao;
import youke.common.dao.IMobcodeTaskDao;
import youke.facade.mass.vo.MassSMSMessage;
import youke.service.mass.biz.IMassSendBiz;

import javax.annotation.Resource;

@Service
public class MassSendBiz extends Base implements IMassSendBiz {

    @Resource
    private IMobcodeDao mobcodeDao;
    @Resource
    private IMobcodeTaskDao mobcodeTaskDao;
    @Resource
    private IMobcodeRecordDao mobcodeRecordDao;

    public void doMass(MassSMSMessage message) {

    }

}
