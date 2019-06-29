package youke.service.user.provider;

import org.springframework.stereotype.Service;
import youke.common.model.vo.result.AccountDataVo;
import youke.facade.user.provider.IHomeService;
import youke.facade.user.vo.WaitDataVo;
import youke.service.user.biz.IHomeBiz;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 12:31
 */
@Service
public class HomeService implements IHomeService {
    @Resource
    private IHomeBiz homeBiz;

    @Override
    public WaitDataVo getWaitData(String dykId) {
        return homeBiz.getWaitData(dykId);
    }

    @Override
    public AccountDataVo getAccontData(String youkeId) {
        return homeBiz.getAccontData(youkeId);
    }
}
