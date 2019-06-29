package youke.service.user.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IMarketActiveDao;
import youke.common.dao.IMarketActiveRecordDao;
import youke.common.dao.IYoukeDao;
import youke.common.model.vo.result.AccountDataVo;
import youke.facade.user.vo.WaitDataVo;
import youke.service.user.biz.IHomeBiz;

import javax.annotation.Resource;

@Service
public class HomeBiz extends Base implements IHomeBiz {

    @Resource
    private IYoukeDao youkeDao;
    @Resource
    private IMarketActiveDao marketActiveDao;
    @Resource
    private IMarketActiveRecordDao marketActiveRecordDao;

    @Override
    public AccountDataVo getAccontData(String youkeId) {
        AccountDataVo dataVo = youkeDao.getAccontData(youkeId);
        return dataVo;
    }

    @Override
    public WaitDataVo getWaitData(String dykId) {
        int ingnum = marketActiveDao.selectActiveNumForWaitData(dykId, 1);
        int waitnum = marketActiveDao.selectActiveNumForWaitData(dykId, 0);
        int waitchecknum = marketActiveRecordDao.selectWaitCheckNum(dykId);
        WaitDataVo vo = new WaitDataVo();
        vo.setIngActiveNum(ingnum);
        vo.setWaitActiveNum(waitnum);
        vo.setWaitCheckNum(waitchecknum);
        return vo;
    }
}
