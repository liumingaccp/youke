package youke.service.pay.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.ISubscrFansIntegralDao;
import youke.common.model.TSubscrFansIntegral;
import youke.common.model.vo.result.IntegralRecordVo;
import youke.common.utils.DateUtil;
import youke.service.pay.biz.IFansIntegralBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FansIntegralBiz extends Base implements IFansIntegralBiz {

    @Resource
    private ISubscrFansIntegralDao subscrFansIntegralDao;

    @Override
    public int getIntegralTotal(String openId) {
        Integer sum = subscrFansIntegralDao.selectSumIntegral(openId);
        return empty(sum)?0:sum;
    }

    @Override
    public List<IntegralRecordVo> getIntegralList(String openId) {
        List<TSubscrFansIntegral> integrals = subscrFansIntegralDao.selectIntegralList(openId);
        List<IntegralRecordVo> recordVos = new ArrayList<>();
        if(integrals!=null)
        {
            for (TSubscrFansIntegral integral:integrals) {
                recordVos.add(new IntegralRecordVo(integral.getId(),integral.getOpenid(),integral.getCometype(),integral.getTitle(),integral.getIntegral(), DateUtil.formatDate(integral.getCreatetime(),"yyyy-MM-dd HH:mm")));
            }
        }
        return recordVos;
    }

    @Override
    public void addIntegral(int comeType, String title, String openId, int integral, String appId, String youkeId) {
        TSubscrFansIntegral bean = new TSubscrFansIntegral();
        bean.setCometype(comeType);
        bean.setAppid(appId);
        bean.setCreatetime(new Date());
        bean.setIntegral(integral);
        bean.setTitle(title);
        bean.setYoukeid(youkeId);
        bean.setOpenid(openId);
        subscrFansIntegralDao.insertSelective(bean);
    }
}
