package youke.service.wx.biz;

import youke.facade.wx.vo.WxTempVo;

import java.util.List;
import java.util.Map;

/**
 * 模板消息处理
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738726
 */
public interface ITemplateBiz {

    List<WxTempVo> getTemplates(String appId);

    int removeTemplate(String appId, String templateId);

    String saveOpenTemplate(String appId, String youkeId, String templateShort);

    void doSendTemplete(String appId, String openId, String tempType, String url, Map<String,String> data);

    void doSendMessage(String appId, String openId, String templeteId, String url,Map<String,String> data);

    boolean doIsOpen(String appId);
}
