package youke.service.shop.biz;

import java.util.Date;

public interface ITbCoreBiz {

    void saveAuthInfo(String dykId, String uid, String userNick, String accessToken, String refreshToken, Date expireTime, Long r2Expires, int shopType);

    String getToken(int shopId);
}
