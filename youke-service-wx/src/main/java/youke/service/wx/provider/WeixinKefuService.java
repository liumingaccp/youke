package youke.service.wx.provider;

import org.springframework.stereotype.Service;
import youke.common.exception.BusinessException;
import youke.common.model.vo.file.SerializeMultipartFile;
import youke.facade.wx.provider.IWeixinKefuService;
import youke.facade.wx.vo.kefu.KefuStateVo;
import youke.facade.wx.vo.kefu.KefuVo;
import youke.service.wx.biz.IKefuBiz;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/6
 * Time: 9:58
 */
@Service
public class WeixinKefuService implements IWeixinKefuService {

    @Resource
    private IKefuBiz kefuBiz;

    public List<KefuVo> getKefus(String appId) {
        return kefuBiz.getKefus(appId);
    }

    public void addKefu(SerializeMultipartFile file, String nickName, String account, String appId) {
        kefuBiz.addKefu(file, nickName,account,appId);
    }

    public void postHeadImage(String account, SerializeMultipartFile file, String appId, int id) {
        kefuBiz.saveHeadImage(account, file ,appId, id);
    }

    public void deleteKefu(String account, String appId, int id) {
        if(account == null || !account.contains("@")){
            throw new BusinessException("账号格式错误");
        }
        kefuBiz.deleteKefu(id ,account, appId);
    }

    public void saveBindKefuWechat(String account, String wechat, String appId, int id) {
        kefuBiz.saveBindKefuWechat(id, account, wechat,appId);
    }

    public List<KefuStateVo> getKefuStateList(String appId) {
        return kefuBiz.getKefuStateList(appId);
    }
}
