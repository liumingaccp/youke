package youke.facade.cloudcode.provider;

import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/31
 * Time: 17:56
 */
public interface IVipKefuService {
    /**
     * 保存vip客服二维码
     * @param url
     * @param remark
     * @param appId
     * @param dykId
     */
    void saveKefuQcode(String url, String remark, String appId, String dykId);

    /**
     * 获取二维码地址
     * @return
     */
    Map getQcodeUrl(String appId);

    void delQcodeUrl(String appId);
}
