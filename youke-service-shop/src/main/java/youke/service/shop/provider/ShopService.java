package youke.service.shop.provider;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.JushitaJdpUserAddRequest;
import com.taobao.api.response.JushitaJdpUserAddResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import youke.common.dao.IShopDao;
import youke.common.exception.BusinessException;
import youke.common.model.vo.result.ShopVo;
import youke.facade.shop.provider.IShopService;
import youke.facade.shop.util.TBConstants;
import youke.facade.shop.vo.AuthShopVo;
import youke.service.shop.biz.*;
import youke.service.shop.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ShopService implements IShopService {

    @Resource
    private IShopBiz shopBiz;
    @Resource
    private IJdCoreBiz jdCoreBiz;
    @Resource
    private ITbCoreBiz tbCoreBiz;
    @Resource
    private IYzCoreBiz yzCoreBiz;
    @Resource
    private IPddCoreBiz pddCoreBiz;
    @Resource
    private QueueSender queueSender;
    @Resource
    private IShopDao shopDao;

    public List<ShopVo> getShopList(String dykId) {
        if (StringUtils.hasLength(dykId)) {
            return shopBiz.getShopList(dykId);
        } else {
            throw new BusinessException("店铺不存在");
        }
    }

    public void saveJDAuth(String dykId, String uid, String userNick, String accessToken, String refreshToken, Date begTime,Date endTime) {
        jdCoreBiz.saveAuthInfo(dykId,uid,userNick,accessToken,refreshToken,begTime,endTime);
    }

    public void savePDDAuth(String dykId, String uid, String userNick, String accessToken, String refreshToken, Long expire) {
        pddCoreBiz.saveAuthInfo(dykId,uid,userNick,accessToken,refreshToken,expire);
    }

    public void saveYZAuth(String dykId, String accessToken, String refreshToken, Long expire) {
        yzCoreBiz.saveAuthInfo(dykId,accessToken,refreshToken,expire);
    }

    public void saveTBAuth(String dykId, String uid, String userNick, String accessToken, String refreshToken, Date expireTime, Long r2Expires, int shopType) {
        tbCoreBiz.saveAuthInfo(dykId,uid,userNick,accessToken,refreshToken,expireTime,r2Expires,shopType);
    }

    public List<AuthShopVo> getAuthShops(String title, int state, int type, String dykId) {
        //更新状态
        shopBiz.updateExpState(dykId);
        return shopBiz.getAuthShops(title,state,type,dykId);
    }

    public String getPDDToken(Integer shopId){
        return pddCoreBiz.getToken(shopId);
    }

    @Override
    public String getTaoKouLing(String url, String text) {
        return "";
    }

    @Override
    public void doYZOrderPush(String dianId,String orderStr) {
        queueSender.send("youzan.order.push",dianId+"#"+orderStr);
    }

}
