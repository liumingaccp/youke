package youke.service.wx.provider;

import org.springframework.stereotype.Service;
import youke.common.model.TSubscrMenuRule;
import youke.facade.wx.provider.IWeixinTempService;
import youke.facade.wx.vo.WxTempVo;
import youke.facade.wx.vo.menu.MenuVo;
import youke.service.wx.biz.IMenuBiz;
import youke.service.wx.biz.ITemplateBiz;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class WeixinTempService implements IWeixinTempService {

    @Resource
    private ITemplateBiz templateBiz;

    @Override
    public boolean isOpen(String appId){
        return templateBiz.doIsOpen(appId);
    }

    @Override
    public List<WxTempVo> getTemplates(String appId) {
        return templateBiz.getTemplates(appId);
    }

    @Override
    public String openTemplate(String appId, String youkeId,String templateShort) {
        return templateBiz.saveOpenTemplate(appId, youkeId,templateShort);
    }

    @Override
    public void closeTemplate(String appId,  String templateId) {
        templateBiz.removeTemplate(appId,templateId);
    }

}
