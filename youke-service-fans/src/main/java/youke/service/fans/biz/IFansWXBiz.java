package youke.service.fans.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.WxFansQueryVo;
import youke.common.model.vo.result.WxFansVo;

import java.util.List;

/**
 * 微信粉丝业务
 */
public interface IFansWXBiz {
    PageInfo<WxFansVo> getList(WxFansQueryVo qo);

    void saveRemark(String openId, String remark);

    PageInfo<WxFansVo> getBlackList(String nickName, int page, int limit, String appId);

    void addTags(String openIds, String tags);

    void deleteTags(String openId, String tags);

    void batchBlack(List<String> openIds, String appid);

    void batchOutBlack(List<String> openIds, String appid);

}
