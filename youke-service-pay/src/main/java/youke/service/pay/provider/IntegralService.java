package youke.service.pay.provider;

import org.springframework.stereotype.Service;
import youke.common.model.vo.result.IntegralRecordVo;
import youke.facade.pay.provider.IIntegralService;
import youke.service.pay.biz.IFansIntegralBiz;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IntegralService implements IIntegralService {

    @Resource
    private IFansIntegralBiz fansIntegralBiz;

    @Override
    public int getIntegralTotal(String openId) {
        return fansIntegralBiz.getIntegralTotal(openId);
    }

    @Override
    public List<IntegralRecordVo> getIntegralList(String openId) {
        return fansIntegralBiz.getIntegralList(openId);
    }

    @Override
    public void addIntegral(int comeType, String title, String openId, int integral, String appId, String youkeId) {
        fansIntegralBiz.addIntegral(comeType,title,openId,integral, appId, youkeId);
    }

}
