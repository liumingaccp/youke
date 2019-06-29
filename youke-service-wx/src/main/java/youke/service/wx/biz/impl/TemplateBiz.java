package youke.service.wx.biz.impl;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.TSubscrMenu;
import youke.common.model.TSubscrMenuRule;
import youke.common.model.TSubscrTemplate;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.util.Constants;
import youke.facade.wx.util.TemplateUtil;
import youke.facade.wx.vo.WxTempVo;
import youke.facade.wx.vo.menu.ButtonVo;
import youke.facade.wx.vo.menu.MenuVo;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.biz.IMenuBiz;
import youke.service.wx.biz.ITemplateBiz;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 * Created By Liuming
 * Date: 2017/12/20
 * Time: 9:24
 */
@Service(value = "templateBiz")
public class TemplateBiz extends Base implements ITemplateBiz{

    @Resource
    private ISubscrTemplateDao subscrTemplateDao;
    @Resource
    private ISubscrDao subscrDao;

    @Resource
    private ICoreBiz coreBiz;

    @Override
    public List<WxTempVo> getTemplates(String appId) {
        List<TSubscrTemplate> templates = subscrTemplateDao.selectTemplates(appId);
        List<WxTempVo> temps = TemplateUtil.getTemps();
        for (WxTempVo tempVo: temps) {
            if(templates!=null&&templates.size()>0){
                for (TSubscrTemplate template: templates) {
                    if(tempVo.getTemplateShort().equals(template.getTemplateshort())){
                        tempVo.setTemplateId(template.getTemplateid());
                        break;
                    }
                }
            }
        }
        return temps;
    }

    @Override
    public int removeTemplate(String appId,String templateId) {
        HttpConnUtil.doPostJson(Constants.BASEURL + "template/del_private_template?access_token=" + coreBiz.getToken(appId), "{\"template_id\":\"" + templateId + "\"}");
        return subscrTemplateDao.deleteTemplate(appId,templateId);
    }

    @Override
    public String saveOpenTemplate(String appId, String youkeId, String templateShort) {
        String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "template/api_add_template?access_token=" + coreBiz.getToken(appId), "{\"template_id_short\":\""+templateShort+"\"}");
        JSONObject jsonObj = JSONObject.fromObject(ret);
        if(jsonObj.has("errcode")){
            if(jsonObj.getInt("errcode")==0){
                String templateId = jsonObj.getString("template_id");
                TSubscrTemplate template = new TSubscrTemplate();
                template.setAppid(appId);
                template.setCreatetime(new Date());
                template.setTemplateid(templateId);
                template.setTemplateshort(templateShort);
                template.setYoukeid(youkeId);
                subscrTemplateDao.insertSelective(template);
                return templateId;
            }
        }
        if(templateShort.equals(TemplateUtil.MONEY_GAIN))
            return null;
        throw new BusinessException("未申请模板消息，请前往微信后台申请");
    }

    @Override
    public void doSendTemplete(String appId, String openId, String tempType, String url, Map<String,String> data) {
        //判断是否开启
        String templateId = subscrTemplateDao.selectTemplateId(appId,tempType);
        if(empty(templateId)){
            templateId = saveOpenTemplate(appId,subscrDao.selectDyk(appId),tempType);
        }
        if(notEmpty(templateId)){
            doSendMessage(appId,openId,templateId,url,data);
        }
    }

    @Override
    public void doSendMessage(String appId,String openId,String templateId, String url, Map<String, String> data) {
        StringBuilder jsonData = new StringBuilder();
        jsonData.append("{\"touser\":\""+openId+"\",\"template_id\":\""+templateId+"\",");
        if(notEmpty(url))
            jsonData.append("\"url\":\""+url+"\",");
        jsonData.append("\"data\":{");
        for (String key : data.keySet()) {
            jsonData.append("\""+key+"\":{\"value\":\"" +data.get(key)+ "\",\"color\":\"#173177\"},");
        }
        jsonData.append("}}");
        String json = jsonData.toString().replace(",}","}");
        System.out.println(HttpConnUtil.doPostJson(Constants.BASEURL + "message/template/send?access_token=" + coreBiz.getToken(appId), json));
    }

    @Override
    public boolean doIsOpen(String appId) {
        String result = HttpConnUtil.doHttpRequest(Constants.BASEURL + "template/get_industry?access_token=" + coreBiz.getToken(appId));
        if(JSONObject.fromObject(result).containsKey("errcode"))
           return false;
        return true;
    }

}
