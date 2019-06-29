package youke.service.shop.biz;

import java.util.Date;

public interface IJdCoreBiz {
    void saveAuthInfo(String dykId, String uid, String userNick, String accessToken, String refreshToken, Date begTime, Date endTime);

    String getToken(int shopId);
}
