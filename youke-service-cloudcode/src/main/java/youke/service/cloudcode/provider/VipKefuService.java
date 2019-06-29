package youke.service.cloudcode.provider;

import org.springframework.stereotype.Service;
import youke.common.exception.BusinessException;
import youke.common.utils.StringUtil;
import youke.facade.cloudcode.provider.IVipKefuService;
import youke.service.cloudcode.biz.IVipKefuBiz;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/31
 * Time: 17:57
 */
@Service
public class VipKefuService implements IVipKefuService{

    @Resource
    private IVipKefuBiz vipKefuBiz;

    @Override
    public void saveKefuQcode(String url, String remark, String appId, String dykId) {
        if(!StringUtil.hasLength(appId)
                || !StringUtil.hasLength(url)){
            throw new BusinessException("无效的参数");
        }
        vipKefuBiz.saveVipKefu(url, remark, appId, dykId);
    }

    @Override
    public Map getQcodeUrl(String appId) {
        return vipKefuBiz.selectUrl(appId);
    }

    @Override
    public void delQcodeUrl(String appId) {
        vipKefuBiz.delVipKefu(appId);
    }
}
