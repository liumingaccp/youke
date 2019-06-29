package youke.facade.wx.provider;

import youke.common.model.TSubscrMenuRule;
import youke.facade.wx.vo.WxTempVo;
import youke.facade.wx.vo.menu.MenuVo;

import java.util.List;
import java.util.Map;

public interface IWeixinTempService {

    /**
     * 是否开启模板
     * @param appId
     * @return
     */
    boolean isOpen(String appId);

    /**
     * 获取微信模板
     * @param appId
     * @return
     */
    List<WxTempVo> getTemplates(String appId);

    /**
     * 开启模板
     * @param appId
     * @param youkeId
     * @param templateShort
     */
    String openTemplate(String appId, String youkeId, String templateShort);

    /**
     * 关闭模板
     * @param appId
     * @param templateId
     */
    void closeTemplate(String appId, String templateId);

}
