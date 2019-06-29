package youke.service.fans.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import youke.common.exception.BusinessException;
import youke.common.model.vo.param.WxFansQueryVo;
import youke.common.model.vo.result.WxFansVo;
import youke.facade.fans.provider.IWxFansService;
import youke.service.fans.biz.IFansWXBiz;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 18:48
 */
@Service
public class WxFansService implements IWxFansService {

    @Resource
    private IFansWXBiz fansWXBiz;

    public PageInfo<WxFansVo> getList(WxFansQueryVo qo) {
        return fansWXBiz.getList(qo);
    }

    @Override
    public long getCount(WxFansQueryVo qo) {
        qo.setLimit(0);
        PageInfo<WxFansVo> list = fansWXBiz.getList(qo);
        return list == null?0:list.getTotal();
    }

    public void saveRemark(String openId, String remark) {
        fansWXBiz.saveRemark(openId, remark);
    }


    public PageInfo<WxFansVo> getListBlack(String nickname, int page, int limit, String appId) {
        if (page < 0) {
            page = 1;
        }
        if (limit < -2) {
            limit = 20;
        }
        return fansWXBiz.getBlackList(nickname, page, limit, appId);
    }


    public void addTags(String openIds, String tags) {
        if (StringUtils.hasLength(openIds) && StringUtils.hasLength(tags)) {
            String[] split = openIds.split(",");
            String[] tagIds = tags.split(",");
            if(split.length <= 0 || tagIds.length <= 0){
                throw new BusinessException("不存在粉丝或者标签");
            }
            fansWXBiz.addTags(openIds, tags);
        } else {
            throw new BusinessException("无效参数");
        }
    }

    public void deleteTags(String openId, String tags) {
        if (StringUtils.hasLength(openId) && StringUtils.hasLength(tags)) {
            fansWXBiz.deleteTags(openId, tags);
        } else {
            throw new BusinessException("无效参数");
        }
    }


    public void batchtags(String openIds, String tags) {
        if (StringUtils.hasLength(openIds) && StringUtils.hasLength(tags)) {
            String[] split = openIds.split(",");
            for (int i = 0; i < split.length; i++) {
                this.addTags(split[i], tags);
            }
        } else {
            throw new BusinessException("无效参数");
        }
    }


    public void batchBlack(List<String> openIds, String appid) {
        if (StringUtils.hasLength(appid) && openIds.size() > 0) {
            fansWXBiz.batchBlack(openIds, appid);
        } else {
            throw new BusinessException("无效参数");
        }
    }


    public void batchOutBlack(List<String> openIds, String appid) {
        if (StringUtils.hasLength(appid) && openIds.size() > 0) {
            fansWXBiz.batchOutBlack(openIds, appid);
        } else {
            throw new BusinessException("无效参数");
        }
    }
}
