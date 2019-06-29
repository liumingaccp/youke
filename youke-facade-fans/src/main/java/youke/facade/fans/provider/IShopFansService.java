package youke.facade.fans.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.file.SerializeMultipartFile;
import youke.common.model.vo.param.ShopFansQueryVo;
import youke.common.model.vo.result.BuyerOrderVo;
import youke.common.model.vo.result.BuyerVo;
import youke.common.model.vo.result.ShopFansImportVo;
import youke.common.model.vo.result.ShopFansVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 10:38
 */
public interface IShopFansService {
    PageInfo<ShopFansVo> getShopfansList(ShopFansQueryVo qo);

    /**
     * 删除购物粉丝的 标签
     * @param fansId
     * @param tags
     */
    void deleteTags(long fansId, String tags);

    void addTags(String fansIds, String tags, String youkeId);

    /**
     * 文件导入的方式增加粉丝
     * @param shopId
     * @param file
     */
    void saveImportFansFromFile(int shopId, SerializeMultipartFile file, String youkeId);

    void saveRemark(long fansId, String remark);

    PageInfo<ShopFansImportVo> getImportList(int page, int limit, String youkeId);

    /**
     * 获取最后同步时间
     * @return
     */
    String getSyncTime();

    /**
     * 同步粉丝
     */
    void sync();

    /**
     * 异步上传购物粉丝
     * @param url
     *      文件地址
     */
    void doImportFans(String url, int shopId, String youkeId, int importId);

    BuyerVo getFriendBuyerInfo(String friendId,String weixinId,String youkeId);

    List<BuyerOrderVo> getShopOrderList(String friendId,String weixinId,String youkeId);

    void bindBuyerName(String friendId, String buyerName,String weixinId,String youkeId);

    void bindMobile(String friendId, String mobile, String weixinId);

    /**
     * 编辑粉丝
     * @param fans
     */
    void saveFans(ShopFansVo fans);

    /**
     * 批量修改粉丝状态
     * @param ids
     * @param state 1:加入黑名单 0:移除黑名单
     *
     */
    void openOrdeleteFans(String ids, int state);
}
