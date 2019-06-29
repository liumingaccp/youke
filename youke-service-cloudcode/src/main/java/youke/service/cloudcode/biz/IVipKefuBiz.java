package youke.service.cloudcode.biz;

import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/31
 * Time: 18:29
 */
public interface IVipKefuBiz {
    void saveVipKefu(String url, String remark, String appId, String dykId);

    Map selectUrl(String appId);

    void delVipKefu(String appId);
}
