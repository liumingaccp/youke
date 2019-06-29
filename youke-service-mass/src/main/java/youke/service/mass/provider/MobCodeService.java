package youke.service.mass.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.exception.BusinessException;
import youke.common.model.TMobcodeOrder;
import youke.common.model.TMobcodePackage;
import youke.common.model.vo.param.QueryObjectVO;
import youke.facade.mass.provider.IMobCodeService;
import youke.service.mass.biz.IMobCodeBiz;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MobCodeService extends Base implements IMobCodeService {

    @Resource
    private IMobCodeBiz mobCodeBiz;

    public Integer getCodeCount(String dykId) {
        if (notEmpty(dykId)) {
            return mobCodeBiz.getCodeCount(dykId);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public List<TMobcodePackage> getPackages() {
        return mobCodeBiz.getPackages();
    }

    public PageInfo<TMobcodeOrder> getOrderList(QueryObjectVO params, String dykId) {
        if (notEmpty(dykId) && notEmpty(params)) {
            return mobCodeBiz.getOrderList(params, dykId);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }
}
