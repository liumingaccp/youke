package youke.facade.fans.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.WxFansQueryVo;
import youke.common.model.vo.result.WxFansVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 18:48
 */
public interface IWxFansService {
    /**
     * 微信粉丝分页获取列表
     *
     * @param qo
     * @return
     */
    PageInfo<WxFansVo> getList(WxFansQueryVo qo);

    long getCount(WxFansQueryVo qo);
    /**
     * 添加备注
     *
     * @param openId
     * @param remark
     */
    void saveRemark(String openId, String remark);

    /**
     * 微信粉丝黑名单分页获取
     *
     * @return
     */
    PageInfo<WxFansVo> getListBlack(String nickName, int page, int limit, String appId);

    /**
     * 添加标签
     *
     * @param openIds
     * @param tags
     */
    void addTags(String openIds, String tags);

    void deleteTags(String openId, String tags);

    /**
     * 批量移除标签
     *
     * @param openIds
     * @param tags
     */
    void batchtags(String openIds, String tags);

    /**
     * 批量拉黑
     *
     * @param openIds
     * @param appid
     */
    void batchBlack(List<String> openIds, String appid);

    /**
     * 批量移除出小黑屋
     *
     * @param openIds
     * @param appid
     */
    void batchOutBlack(List<String> openIds, String appid);
}
