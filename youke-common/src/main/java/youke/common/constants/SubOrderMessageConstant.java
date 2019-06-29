package youke.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/6/16
 * Time: 14:16
 *
 *  销量助手 微淘客,试用福利,购物返利参与活动填写订单号通知
 */
public class SubOrderMessageConstant {

    public static final Integer TAO_KE = 1;
    public static final Integer SHI_YONG = 2;
    public static final Integer GOU_WU_FAN_LI = 3;
    public static final Integer JI_FEN_DUI_HUAN = 4;

    public static final String DESCRIPTION = "成功参加【{TITLE}】活动，填写订单号拿返利。";

    public static final Map<Integer, Map<String, String>> TYPE = new HashMap<>();

    static {
        Map<String, String> tem0 = new HashMap<>();
        tem0.put("title","微淘客");
        tem0.put("url", "https://m.dianyk.com/#/micro-amoy?appId={APPID}&type=0");
        TYPE.put(TAO_KE, tem0);

        Map<String, String> tem1 = new HashMap<>();
        tem1.put("title","试用福利");
        tem1.put("url", "https://m.dianyk.com/#/probation-welfare?appId={APPID}");
        TYPE.put(SHI_YONG,tem1);

        Map<String, String> tem2 = new HashMap<>();
        tem2.put("title","购物返利");
        tem2.put("url", "https://m.dianyk.com/#/shopping-return?appId={APPID}");
        TYPE.put(GOU_WU_FAN_LI, tem2);

        Map<String, String> tem3 = new HashMap<>();
        tem3.put("title","积分兑换");
        tem3.put("url", "https://m.dianyk.com/#/my-exchange?appId={APPID}");
        TYPE.put(JI_FEN_DUI_HUAN, tem3);
    }
}
