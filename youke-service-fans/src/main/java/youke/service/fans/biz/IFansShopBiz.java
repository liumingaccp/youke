package youke.service.fans.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.TShopFans;
import youke.common.model.TShopFansImport;
import youke.common.model.vo.param.ShopFansQueryVo;
import youke.common.model.vo.result.BuyerOrderVo;
import youke.common.model.vo.result.BuyerVo;
import youke.common.model.vo.result.ShopFansImportVo;
import youke.common.model.vo.result.ShopFansVo;

import java.util.List;

/**
 * 购物粉丝业务
 */
public interface IFansShopBiz {


    PageInfo<ShopFansVo> getList(ShopFansQueryVo qo);

    void deleteTags(long fansId, String tags);

    void addTags(long fansId, String tags, String youkeId);

    int saveImportFansFromFile(int shopId, String url, String fileName, String youkeId);

    void saveRemark(long fansId, String remark);

    PageInfo<ShopFansImportVo> getImportList(int page, int limit,String dykId);

    int add(TShopFans fans);

    void updateImmport(TShopFansImport tShopFansImport);

    BuyerVo getFriendBuyerInfo(String friendId,String weixinId,String youkeId);

    List<BuyerOrderVo> getShopOrderList(String friendId,String weixinId,String youkeId);

    void saveBuyerName(String friendId, String buyerName,String weixinId,String youkeId);

    /**
     * 检查是否存在此昵称的粉丝
     * @param nickName
     * @param youkeId
     * @return
     */
    Integer selectByNickName(String nickName, String youkeId);

    void saveFriendMobile(String friendId, String mobile, String weixinId);

    /**
     * 编辑粉丝
     * @param fans
     */
    void saveFans(ShopFansVo fans);

    void updateFansState(List<String> ids, int state);
}
