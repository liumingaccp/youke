package youke.service.pay.provider;

import org.springframework.stereotype.Service;
import youke.facade.pay.provider.IMoneyService;
import youke.service.pay.biz.IFansMoneyBiz;

import javax.annotation.Resource;

@Service
public class MoneyService implements IMoneyService {

    @Resource
    private IFansMoneyBiz fansMoneyBiz;

    @Override
    public void addMoney(Integer comeType, String title, String openId, int money, String appId, String youkeId) {
        fansMoneyBiz.addMoney(comeType, title, openId, money, appId, youkeId);
    }
}
