package youke.facade.shop.provider;

import youke.common.model.vo.result.ShopVo;
import youke.facade.shop.vo.AuthShopVo;

import java.util.Date;
import java.util.List;

public interface IShopService {

    /**
     * 获取店铺选择列表
     *
     * @param dykId
     * @return
     */
    List<ShopVo> getShopList(String dykId);

    /**
     * 保存京东授权信息
     * @param dykId
     * @param uid
     * @param userNick
     * @param accessToken
     * @param refreshToken
     * @param begTime
     * @param endTime
     */
    void saveJDAuth(String dykId, String uid, String userNick, String accessToken, String refreshToken, Date begTime,Date endTime);

    /**
     * 保存拼多多授权信息
     * @param dykId
     * @param uid
     * @param userNick
     * @param accessToken
     * @param refreshToken
     * @param expire 过期时间 秒
     */
    void savePDDAuth(String dykId, String uid, String userNick, String accessToken, String refreshToken, Long expire);

    /**
     * 保存淘宝授权信息
     * @param dykId
     * @param uid
     * @param userNick
     * @param accessToken
     * @param refreshToken
     * @param expireTime
     * @param r2Expires
     * @param shopType
     */
    void saveTBAuth(String dykId, String uid, String userNick, String accessToken, String refreshToken, Date expireTime, Long r2Expires, int shopType);

    /**
     * 保存有赞授权信息
     * @param dykId
     * @param accessToken
     * @param refreshToken
     * @param expire
     */
    void saveYZAuth(String dykId, String accessToken, String refreshToken, Long expire);
    /**
     * 获取授权店铺列表
     * @param title
     * @param state
     * @param type
     * @param dykId
     * @return
     */
    List<AuthShopVo> getAuthShops(String title, int state, int type, String dykId);

    /**
     * 获取拼多多店铺token
     * @param shopId
     * @return
     */
    String getPDDToken(Integer shopId);

    /**
     * 生成淘口令(暂时返回"")
     * @return
     */
    String getTaoKouLing(String url, String text);

    void doYZOrderPush(String dianId,String orderStr);
}
