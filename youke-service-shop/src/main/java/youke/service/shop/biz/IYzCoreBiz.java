package youke.service.shop.biz;

public interface IYzCoreBiz {

    void saveAuthInfo(String dykId,String accessToken, String refreshToken, Long expire);

    String getToken(int shopId);
}
