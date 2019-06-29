package youke.facade.wx.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import youke.facade.wx.vo.WxTempVo;

import java.util.ArrayList;
import java.util.List;

public class TemplateUtil {

    public final static String HELPER_KANJIA = "OPENTM410292733"; //砍价成功提醒
    public final static String HELPER_PINTUAN = "OPENTM413295703"; //拼团成功通知
    public final static String EXAMINE_SUCCESS = "OPENTM405933510"; //审核通过
    public final static String EXAMINE_FAIL = "OPENTM207197016"; //审核不通过
//    public final static String SIGN_NOTICE = "OPENTM207941156"; //签到提醒
    public final static String INTEGRAL_GAIN = "OPENTM206903802"; //积分获取通知
    public final static String INTEGRAL_CONSUME = "OPENTM206173246"; //积分消耗通知
    public final static String ORDER_RECEIVE = "OPENTM413601333"; //订单确认收货
    public final static String ORDER_PAY = "OPENTM204987032"; //订单支付成功
    public final static String ORDER_DELIVER = "OPENTM202243318"; //订单发货通知
    public final static String MONEY_GAIN = "OPENTM401588951"; //零钱到帐通知

    public final static String SYS_TEMPID_OPEN = "2p5FlFzh3vdnZqMQErr5e27uG7aooqN-jlJrqq-8G4s"; //店有客开通成功模板Id
    public final static String SYS_TEMPID_CERT = "4DBO0QYrm0d5_zNvGfXoOI8567MUgL045s_2Q8saAkI"; //店有客证书到期模板Id
    public final static String SYS_TEMPID_EXPIRE = "9WsJ9NJb22o3k8ZeWVoTDB2szh7QVmZuxNTCz0silMI"; //店有客会员到期模板Id
    public final static String SYS_TEMPID_RENEW = "SbaxrbBCv0o28fCrfCIA_A6My8eA30Qm5kZXg5zP_uA"; //店有客续费成功模板Id
    public final static String SYS_TEMPID_RECHARGE = "ki-1qxWZbT2qjzk1I2sYA2YdFrhlDjcjTBMaCfFeegQ"; //店有客会员充值模板Id
    public final static String SYS_TEMPID_SERVER = "mXQDMl0S57eOIZ5iu-zu1rrtD-8NAyvgGWJSMHRJtdQ"; //店有客服务到期模板Id
    public final static String SYS_TEMPID_PAYFAIL = "OI7_72HsTYbepxieHfKqwniHsdYlDEic4cheg3v4uBE"; //店有客支付失败模板Id
    public final static String SYS_TEMPID_BALANCE = "vQUuqf5en29dvjQwJig2n778UhofesB-DqC4zAgs4ow"; //账户余额不足

    public final static String SYS_TEMPID_YKSUCC = "dKQRqom9jt9HulVC89xdQ9tWMIjUfLpY_4X8_cJC9Wk"; //店有客审核通过

    private final static String tempData = "[" +
            "{\"title\":\"砍价成功提醒\",\"templateShort\":\"OPENTM410292733\",\"type\":1}," +
            "{\"title\":\"拼团成功通知\",\"templateShort\":\"OPENTM413295703\",\"type\":1}," +
            "{\"title\":\"活动审核通过通知\",\"templateShort\":\"OPENTM405933510\",\"type\":1}," +
            "{\"title\":\"活动审核通过通知\",\"templateShort\":\"OPENTM405933510\",\"type\":1}," +
            "{\"title\":\"订单确认收货关怀\",\"templateShort\":\"OPENTM413601333\",\"type\":0}," +
            "{\"title\":\"活动审核不通过通知\",\"templateShort\":\"OPENTM207197016\",\"type\":1}," +
//            "{\"title\":\"签到提醒\",\"templateShort\":\"OPENTM207941156\",\"type\":1}," +
            "{\"title\":\"积分获取通知\",\"templateShort\":\"OPENTM206903802\",\"type\":1}," +
            "{\"title\":\"积分扣除通知\",\"templateShort\":\"OPENTM206173246\",\"type\":1}," +
            "{\"title\":\"订单支付成功通知\",\"templateShort\":\"OPENTM204987032\",\"type\":0}," +
            "{\"title\":\"订单发货通知\",\"templateShort\":\"OPENTM202243318\",\"type\":0}]";


    public static List<WxTempVo> getTemps(){
        List<WxTempVo> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.fromObject(tempData);
        for (int i=0;i<jsonArray.size();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            list.add(new WxTempVo(obj.getString("title"),obj.getString("templateShort"),obj.getInt("type")));
        }
        return list;
    }

}
