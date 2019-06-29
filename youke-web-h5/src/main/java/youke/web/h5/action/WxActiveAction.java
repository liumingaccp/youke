package youke.web.h5.action;


import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.facade.wx.provider.IWeixinMessageService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("wxactive")
public class WxActiveAction extends BaseAction {

    @Resource
    private IWeixinMessageService weixinMessageService;

    //派代年会
    @RequestMapping(value="saveuser")
    @ResponseBody
    public JsonResult saveuser() {
        String key = "H5-MARKET-SAVEUSER";
        Date now = new Date();
        JSONObject object = getParams();
        if(!object.containsKey("truename"))
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"姓名不能为空");
        if(!object.containsKey("company"))
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"公司不能为空");
        if(!object.containsKey("mobile"))
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"手机不能为空");
        String truename = object.getString("truename");
        String company = object.getString("company");
        String mobile = object.getString("mobile");
        if(RedisUtil.hHasKey(key,mobile)){
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"该手机号已经申请加入了");
        }
        object.put("state",0);
        object.put("createTime",DateUtil.formatDate(now,"yyyy-MM-dd HH:mm:ss"));
        RedisUtil.hset(key,mobile,object.toString());
        String position = "";
        if(object.containsKey("position"))
            position = object.getString("position");


        try {
            Map<String,String> map = new HashMap<>();
            map.put("first","您好，派代年会有新的商家申请加入店有客啦！");
            map.put("keyword1", truename+"("+mobile+")-"+position+"-"+company);
            map.put("keyword2", DateUtil.formatDate(now,"yyyy-MM-dd HH:mm"));
            map.put("remark","请登录业务后台跟进处理。");

            String appId = "wxb90758151405384b";
            String tempId = "-_WTOd34ODrlRTja9LBPOGZ9zlc-DbZi-8RG_8KnQZg";
            String url="https://sys.dianyk.com";
            String openIds = (String)RedisSlaveUtil.get(key+"-OPENID");
            for (String openId:openIds.split(",")) {
                weixinMessageService.sendTempMess(appId, openId, tempId, url, map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JsonResult();
    }

}
