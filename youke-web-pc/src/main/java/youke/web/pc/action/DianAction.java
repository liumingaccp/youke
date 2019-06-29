package youke.web.pc.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import youke.facade.shop.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 店铺授权业务
 */
@Controller
@RequestMapping("/dian")
public class DianAction extends BaseAction {

    @RequestMapping(value="gobook")
    public String goauth(HttpServletRequest request) {
        int type = 0;
        if(notEmpty(request.getParameter("type"))){
            type = Integer.parseInt(request.getParameter("type"));
        }
        if(type<2)
            return "redirect:https://fuwu.taobao.com/ser/detail.htm?service_code=ts-11997";
        if(type==2)
            return "redirect:https://fw.jd.com/84803.html";
        return "";
    }


    /**
     * 跳转授权页面
     * @return
     */
    @RequestMapping(value="{dykId}/goauth")
    public String goauth(@PathVariable String dykId,HttpServletRequest request){
        String view = "web";
        int type = 0;
        if(notEmpty(request.getParameter("type"))){
            type = Integer.parseInt(request.getParameter("type"));
        }
        if(type==ShopType.TMALL){
            view = "tmall";
            dykId = dykId+"_"+type;
        }
        if(type<2)
           return "redirect:https://oauth.taobao.com/authorize?response_type=code&client_id="+TBConstants.OPENAPPKEY+"&redirect_uri=http://www.pudada.com/tb/authback&state="+dykId+"&view="+view;
        if(type==2)
           return "redirect:https://oauth.jd.com/oauth/authorize?response_type=code&client_id="+ JDConstants.OPENAPPKEY+"&redirect_uri=http://jdser.dianyk.com/jd/authback&state="+dykId;
        if(type==3)
           return "redirect:http://mms.pinduoduo.com/open.html?response_type=code&client_id="+ PDDConstants.CLENTID+"&redirect_uri=https://pcapi.dianyk.com/pinduoduo/authback&state="+dykId;
        if(type==4)
            return "redirect:https://open.youzan.com/oauth/authorize?client_id="+ YZConstants.CLENTID+"&response_type=code&redirect_uri=https://pcapi.dianyk.com/youzan/authback&state="+dykId;
        return "";
    }

}
