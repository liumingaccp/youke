package youke.service.wx.biz;

import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import youke.common.model.vo.file.SerializeMultipartFile;
import youke.facade.wx.vo.MediaVo;
import youke.facade.wx.vo.material.*;

import java.util.List;

/**
 * 素材管理业务
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738726
 */
public interface IMaterialBiz {


    /**
     * 上传图文素材,返回增加了内容 news 集合, 用于本地数据库的增加
     */
    List<NewsVo> uploadNews(List<NewsVo> news, String appId);

    /**
     * 上传其他素材
     *
     * @param file
     * @return media_id    新增的永久素材的media_id
     * url	新增的图片素材的图片URL（仅新增图片素材时会返回该字段）
     */
    MediaVo uploadMaterial(SerializeMultipartFile file, String appId);

    /**
     * 从远程地址上传一个素材
     */
    MediaVo uploadMaterial(String fileUrl, String appId);

    /**
     * 获取临时素材(返回访问的id)
     */
    String uploadThumbMediaId(MultipartFile file, String appId);

    /**
     * 上传图文消息内的图片,获取url（仅支持jpg/png格式，大小必须在1MB以下,临时素材）
     *
     * @param file
     * @return
     */
    MediaVo uploadImg(MultipartFile file, String appId);

    /**
     * 添加图文
     *
     * @param news
     * @param appId
     * @param userId
     */
    int addNews(List<NewsVo> news, String appId, int userId);

    /**
     * 添加图片
     *
     * @param image
     */
    int addImage(ImageVo image);

    /**
     * 添加其他素材
     *
     * @param file
     */
    void addFile(FileVo file);

    /**
     * 添加文本
     *
     * @param text
     */
    void addText(TextVo text);

    /**
     * 删除图文
     *
     * @param id
     */
    void deleteNews(int id, String appId);

    /**
     * 批量删除图片
     *
     * @param ids
     */
    void deleteImage(String ids, String appId);

    /**
     * 删除文件
     *
     * @param id
     */
    void delFile(int id, String appId);

    /**
     * 删除文本
     *
     * @param id
     */
    void delText(int id);

    /**
     * 修改图文
     *
     * @param news
     * @param userId
     * @param id
     */
    void updateNews(List<NewsVo> news, String appId, String userId, int id);

    /**
     * 修改文本
     *
     * @param text
     */
    void updateText(TextVo text);

    /**
     * 获取本地素材,用于编辑
     *
     * @param appId
     * @param type
     * @return
     */
    List getMaters(String appId, String type);

    /**
     * 获取图片素材
     */
    PageInfo<ImageVo> getImageMaters(int page, int limit, String appId);

    /**
     * 获取视频素材
     */
    List<FileVo> getFileMaters(String appId);

    /**
     * 获取文本素材
     *
     * @param appId
     * @return
     */
    List<TextVo> getTextMaters(String appId);

    /**
     * 分页获取图文列表
     *
     * @param appId
     * @return
     */
    PageInfo<NewsTreeVo> getNewsList(String appId, int page, int limit);

    /**
     * 获取子图文
     *
     * @param id
     * @return
     */
    List<NewsVo> getSubNews(Integer id);

    /**
     * 获取单个完整图文
     *
     * @param id
     * @return
     */
    NewsTreeVo getNews(Integer id, String appId);


    /**
     * 分页获取图文列表
     *
     * @param appId
     * @return
     */
    PageInfo<SysNewsTreeVo> getSysNewsList(String appId, int page, int limit, int type);

    /**
     * 获取系统子图文
     *
     * @param id
     * @return
     */
    List<SysnewsVo> getSubSysNews(Integer id);

    /**
     * 获取系统图文
     *
     * @param id
     * @return
     */
    SysNewsTreeVo getSysnews(Integer id, String appId);


    /**
     * 获取单个图片素材
     *
     * @param id
     * @param appId
     * @return
     */
    ImageVo getImg(int id, String appId);

    /**
     * 更新图片素材标题
     *
     * @param id
     * @param title
     * @param appId
     */
    void updateImg(int id, String title, String appId);

    /**
     * 增加系统图文
     *
     * @param acticles
     */
    int addSysnews(List<SysnewsVo> acticles, String appId);

    /**
     * 更新系统图文
     *
     * @param acticles
     * @param id
     */
    void saveSysnews(List<SysnewsVo> acticles, Integer id, String appId);

    void saveTran();

    void deleteImageForWX(List<String> ids, String appId);

    /**
     * 同步微信图片素材
     *
     * @param appId
     */
    void doSyncImage(String appId);

    /**
     * 同步微图文素材
     *
     * @param appId
     */
    void doSyncNews(String appId);

    /**
     * 获取素材对应的id
     *
     * @param appId
     * @param value
     * @param mediatype
     * @return
     */
    Integer getMaterByMaterialId(String appId, String value, String mediatype);

    /**
     * 上传图文消息内图片
     * @param file
     * @param appId
     * @return
     */
    String uploadNewsImg(SerializeMultipartFile file, String appId);
}