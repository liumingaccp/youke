package youke.common.exception;

import net.sf.json.JSONObject;
import youke.common.bean.JsonResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2017/12/28
 * Time: 11:11
 */
public class WxException extends RuntimeException implements Serializable {
    public static final Map<Integer, String> map = new HashMap<>();

    static {
        map.put(-1, "系统繁忙，此时请开发者稍候再试");
        map.put(65400,"API不可用，即没有开通/升级到新版客服功能");
        map.put(65401,"无效客服帐号");
        map.put(65403,"客服昵称不合法");
        map.put(65404,"客服帐号不合法");
        map.put(65405,"帐号数目已达到上限，不能继续添加");
        map.put(65406,"已经存在的客服帐号");
        map.put(65407,"邀请对象已经是该公众号客服");
        map.put(65408,"本公众号已经有一个邀请给该微信");
        map.put(65409,"无效的微信号");
        map.put(65410,"邀请对象绑定公众号客服数达到上限（目前每个微信号可以绑定5个公众号客服帐号）");
        map.put(65411,"该帐号已经有一个等待确认的邀请，不能重复邀请");
        map.put(65412,"该帐号已经绑定微信号，不能进行邀请");
        map.put(40005,"不支持的媒体类型");
        map.put(40009,"媒体文件长度不合法");
        map.put(40007,"图片素材已失效,请先上传图片素材");
        map.put(40027, "不合法的子菜单按钮URL长度");
        map.put(40020, "不合法的按钮URL长度");
        map.put(40013, "不合法的APPID");
        map.put(40014, "不合法的access_token");
        map.put(40015, "不合法的菜单类型");
        map.put(40016, "不合法的按钮个数");
        map.put(40017, "不合法的按钮个数");
        map.put(40022,"不合法的子菜单级数");
        map.put(40023, "不合法的子菜单按钮个数");
        map.put(40024, "不合法的子菜单按钮类型");
        map.put(41002, "缺少appid参数");
        map.put(61004, "服务器IP未经授权");
    }

    public WxException(String message) {
        super(message);
    }

    public WxException(String message, int code, Object data) {
        super("code:"+code+",message:"+message+",data:"+data);
    }
}

