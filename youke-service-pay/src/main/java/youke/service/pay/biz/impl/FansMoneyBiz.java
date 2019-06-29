package youke.service.pay.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.ISubscrFansMoneyDao;
import youke.common.model.TSubscrFansMoney;
import youke.common.model.vo.result.MoneyRecordVo;
import youke.common.utils.DateUtil;
import youke.service.pay.biz.IFansMoneyBiz;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FansMoneyBiz extends Base implements IFansMoneyBiz {

    @Resource
    private ISubscrFansMoneyDao subscrFansMoneyDao;

    @Override
    public int getMoneyTotal(String openId) {
        Integer sum = subscrFansMoneyDao.selectSumMoney(openId);
        return empty(sum)?0:sum;
    }

    @Override
    public List<MoneyRecordVo> getMoneyList(String openId) {
        List<TSubscrFansMoney> moneys = subscrFansMoneyDao.selectMoneyList(openId);
        List<MoneyRecordVo> recordVos = new ArrayList<>();
        if(moneys!=null)
        {
            for (TSubscrFansMoney money:moneys) {
                recordVos.add(new MoneyRecordVo(money.getId(),money.getOpenid(),money.getCometype(), money.getTitle(),money.getMoney(), DateUtil.formatDate(money.getCreatetime(),"yyyy-MM-dd HH:mm")));
            }
        }
        return recordVos;
    }

    @Override
    public void addMoney(Integer comeType,String title, String openId, int money, String appId, String youkeId) {
        TSubscrFansMoney bean = new TSubscrFansMoney();
        bean.setCometype(comeType);
        bean.setAppid(appId);
        bean.setCreatetime(new Date());
        bean.setMoney(money);
        bean.setTitle(title);
        bean.setYoukeid(youkeId);
        bean.setOpenid(openId);
        subscrFansMoneyDao.insertSelective(bean);
    }
}
