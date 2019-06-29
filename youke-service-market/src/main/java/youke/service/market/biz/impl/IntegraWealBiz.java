package youke.service.market.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.dao.*;
import youke.common.model.TIntegralActive;
import youke.common.model.TSubscrFansIntegral;
import youke.common.model.vo.param.WealIntegralQueryVo;
import youke.common.model.vo.param.WealOrderQueryVo;
import youke.common.model.vo.result.IntegralActiveDetailVo;
import youke.common.model.vo.result.IntegralActiveVo;
import youke.common.model.vo.result.IntegralOrderVo;
import youke.common.model.vo.result.SubFansIntegralVo;
import youke.common.model.vo.result.helper.TaokeQueryRetVo;
import youke.service.market.biz.IIntegraWealBiz;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/31
 * Time: 11:56
 */
@Service
public class IntegraWealBiz implements IIntegraWealBiz{

    @Resource
    private IIntegralActiveDao integralActiveDao;
    @Resource
    private IIntegralOrderDao integralOrderDao;
    @Resource
    private ISubscrFansIntegralDao subscrFansIntegralDao;
    @Resource
    private IConfigDao configDao;
    @Resource
    private ISubscrDao subscrDao;

    public PageInfo<IntegralActiveVo> getActiveList(String title, int state, int page, int limit, String youkeId) {
        PageHelper.startPage(page, limit, "createTime desc");
        List<IntegralActiveVo> list = integralActiveDao.queryList(title, state, youkeId);
        return new PageInfo<>(list);
    }

    public int addIntegraActive(TIntegralActive model) {
        integralActiveDao.insertSelective(model);
        return model.getId();
    }

    public PageInfo<IntegralOrderVo> getOrderList(WealOrderQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit(), "createTime desc");
        List<IntegralOrderVo> list = integralOrderDao.queryList(params);
        return new PageInfo<>(list);
    }

    public long getTotalIntegral(String dykId) {
        return integralOrderDao.countIntegral(dykId);
    }

    public PageInfo<SubFansIntegralVo> getIntegralList(WealIntegralQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit(), "sfi.createTime desc");
        List<SubFansIntegralVo> list = subscrFansIntegralDao.queryList(params);
        return new PageInfo<>(list);
    }

    @Override
    public void updateActice(TIntegralActive info) {
        if(info != null && info.getId() != null){
            integralActiveDao.updateByPrimaryKeySelective(info);
        }
    }

    @Override
    public IntegralActiveDetailVo getActice(int activeId, String appId) {
        String dyk = subscrDao.selectDyk(appId);
        return integralActiveDao.getActiceById(activeId, dyk, appId);
    }

    @Override
    public Long getIntegralCount(WealIntegralQueryVo params) {

        return subscrFansIntegralDao.selectCount(params);
    }

    @Override
    public Long getIntegralOrderCount(WealOrderQueryVo params) {
        return integralOrderDao.selectInttegralCount(params);
    }
}
