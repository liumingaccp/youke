package youke.service.shop.biz;

import java.util.Date;

public interface IPddCoreBiz {

    void saveAuthInfo(String dykId, String uid, String userNick, String accessToken, String refreshToken, Long expire);

    String getToken(int shopId);
}
