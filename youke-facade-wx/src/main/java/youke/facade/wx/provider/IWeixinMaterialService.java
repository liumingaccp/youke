package youke.facade.wx.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.file.SerializeMultipartFile;
import youke.facade.wx.vo.material.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/6
 * Time: 10:13
 */
public interface IWeixinMaterialService {
    int addImg(SerializeMultipartFile file, Integer userId, String appId);

    void deleteImg(String ids, String appId);

    void deleteImgForWX(List<String> ids, String appId);

    PageInfo<ImageVo> getImgList(int page, int limit, String appId);

    ImageVo getImg(int id, String appId);

    void updateImgTitle(int id, String title, String appId);

    PageInfo<NewsTreeVo> getNewsList(int page, int limit, String appId);

    NewsTreeVo getNews(int id, String appId);

    int addNews(List<NewsVo> news, String appId, Integer userId, Integer id);

    void delNews(int id, String appId);

    SysNewsTreeVo getSysnews(int id, String appId);

    PageInfo<SysNewsTreeVo> getSysnewsList(int page, int limit, String appId);

    int addOrSaveSysnews(List<SysnewsVo> acticles, Integer id, String appId);

    PageInfo<SysNewsTreeVo> getMarketNewsList(int page, int limit, String appId);

    PageInfo<SysNewsTreeVo> querySysnewsList(int page, int limit, String appId, int type);

    void deleteNewsForWX(String mediaId, String appId);

    void addNewsForWx(String appId, List<NewsVo> news);
}
