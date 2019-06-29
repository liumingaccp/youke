package youke.service.wx.biz.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import youke.common.constants.ApiCodeConstant;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.*;
import youke.common.model.vo.file.SerializeMultipartFile;
import youke.common.model.vo.page.PageModel;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.JsonUtils;
import youke.common.utils.StringUtil;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.util.Constants;
import youke.facade.wx.util.MediaType;
import youke.facade.wx.vo.MediaVo;
import youke.facade.wx.vo.material.*;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.biz.IMaterialBiz;
import youke.service.wx.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

;

/**
 * Created with IntelliJ IDEA
 * Created By Kinsey
 * Date: 2017/12/25
 * Time: 10:49
 */
@Service
public class MaterialBiz implements IMaterialBiz {

    @Resource
    private IMaterialImageDao materialImageDao;
    @Resource
    private IMaterialNewsDao materialNewsDao;
    @Resource
    private IMaterialFileDao materialFileDao;
    @Resource
    private IMaterialTextDao materialTextDao;
    @Resource
    private IMaterialSysnewsDao materialSysnewsDao;
    @Resource
    private ICoreBiz coreBiz;
    @Resource
    private QueueSender queueSender;
    @Resource
    private ISubscrMenuDao subscrMenuDao;
    @Resource
    private IReplyAutoDao replyAutoDao;

    public List<NewsVo> uploadNews(List<NewsVo> news, String appId) {
        //拼接aticle
        StringBuffer aticleJson = new StringBuffer();
        if (news == null || news.size() <= 0) {
            throw new BusinessException("参数异常");
        }
        aticleJson.append("{ \"articles\": [");
        for (NewsVo vo : news) {
            aticleJson.append(newsToJsonStr(vo)).append(",");
        }
        aticleJson.deleteCharAt(aticleJson.length() - 1);
        aticleJson.append("]}");

        String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "material/add_news?access_token=" + coreBiz.getToken(appId), aticleJson.toString());
        WxCurlUtil.ret(ret);
        JSONObject mediaJson = JSONObject.fromObject(ret);

        String mediaId = mediaJson.getString("media_id");

        //获取原文访问的url
        String jsonStr = "{" + "\"media_id\":" + "\"" + mediaId + "\"" + "}";
        String urlRet = HttpConnUtil.doPostJson(Constants.BASEURL + "material/get_material?access_token=" + coreBiz.getToken(appId), jsonStr);
        WxCurlUtil.ret(urlRet);
        List<String> urls = getUrls(urlRet);

        for (int i = 0; i < news.size(); i++) {
            //设置 mediaId
            news.get(i).setMediaId(mediaId);
            //设置原文页的url
            news.get(i).setUrl(urls.get(i));
        }
        return news;

    }

    public List<String> getUrls(String jsonStr) {
        List<String> list = new ArrayList<>();
        JSONObject mediaJson = JSONObject.fromObject(jsonStr);
        JSONArray newsItem = mediaJson.getJSONArray("news_item");
        for (int i = 0; i < newsItem.size(); i++) {
            list.add(newsItem.getJSONObject(i).getString("url"));
        }

        return list;
    }

    /**
     * 辅助方法
     *
     * @param vo
     * @return
     */
    public String newsToJsonStr(NewsVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (vo.getThumbMediaId() != null) {
            map.put("thumb_media_id", vo.getThumbMediaId());
        }
        if (vo.getAuthor() != null) {
            map.put("author", vo.getAuthor());
        }
        if (vo.getTitle() != null) {
            map.put("title", vo.getTitle());
        }
        if (vo.getLink() != null) {
            map.put("content_source_url", vo.getLink());
        }
        if (vo.getContent() != null) {
            map.put("content", vo.getContent());
        }
        if (vo.getIntro() != null) {
            map.put("digest", vo.getIntro());
        }
        if (vo.isShowCover()) {
            map.put("show_cover_pic", 1);
        } else {
            map.put("show_cover_pic", 0);
        }

        return JSON.toJSONString(map);
    }

    /**
     * 上传素材(除开图文,图文需要更多的设置)
     *
     * @param file
     * @return
     */
    public MediaVo uploadMaterial(SerializeMultipartFile file, String appId) {
        String type = null;
        //根据文件后缀,判断类型
        String extensionName = FileUpOrDwUtil.getExtensionName(file.getOriginalFilename());
        if (ApiCodeConstant.hasType(ApiCodeConstant.imageLists, extensionName)) {
            type = "image";
        } else if (ApiCodeConstant.hasType(ApiCodeConstant.voiceLists, extensionName)) {
            type = "voice";
        } else if (ApiCodeConstant.hasType(ApiCodeConstant.videoLists, extensionName)) {
            type = "video";
        } else if (ApiCodeConstant.hasType(ApiCodeConstant.thumbLists, extensionName)) {
            type = "thumb";
        }

        //存储进 阿里云
        String oss_url = null;
        String uuidName = UUID.randomUUID().toString();
        String exName = FileUpOrDwUtil.getExtensionName(file.getOriginalFilename());
        File tempFile = null;
        FileOutputStream stream = null;
        BufferedOutputStream bufferedOutput = null;
        try {
            tempFile = File.createTempFile(uuidName, "." + exName);
            stream = new FileOutputStream(tempFile);
            bufferedOutput = new BufferedOutputStream(stream);
            bufferedOutput.write(file.getBytes());
            bufferedOutput.close();
            stream.close();
            oss_url = FileUpOrDwUtil.uploadFileOrStream(tempFile, type + "/" + tempFile.getName(), false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tempFile.deleteOnExit();
        }

        String postUrl = Constants.BASEURL + "material/add_material?access_token=" + coreBiz.getToken(appId) + "&type=" + type;
        String ret = WxCurlUtil.postFile(postUrl, tempFile);
        WxCurlUtil.ret(ret);
        JSONObject mediaJson = JSONObject.fromObject(ret);
        String mediaId = mediaJson.getString("media_id");
        MediaVo mediaVo = new MediaVo();
        mediaVo.setMediaId(mediaId);
        mediaVo.setUrl(oss_url);
        if (mediaJson.has("url")) {
            String wxUrl = mediaJson.getString("url");
            mediaVo.setWxUrl(wxUrl);
        }

        return mediaVo;
    }

    /**
     * 上传素材(除开图文,图文需要更多的设置)
     *
     * @param filename
     * @return
     */
    public MediaVo uploadMaterial(String filename, String appId) {
        String type = "temp";
        //根据文件后缀,判断类型
        String extensionName = FileUpOrDwUtil.getExtensionName(filename);
        if (ApiCodeConstant.hasType(ApiCodeConstant.imageLists, extensionName)) {
            type = "image";
        } else if (ApiCodeConstant.hasType(ApiCodeConstant.voiceLists, extensionName)) {
            type = "voice";
        } else if (ApiCodeConstant.hasType(ApiCodeConstant.videoLists, extensionName)) {
            type = "video";
        } else if (ApiCodeConstant.hasType(ApiCodeConstant.thumbLists, extensionName)) {
            type = "thumb";
        }

        String postUrl = Constants.BASEURL + "material/add_material?access_token=" + coreBiz.getToken(appId) + "&type=" + type;
        //存储进 阿里云
        String oss_url = null;
        try {
            if (filename != null && filename.contains(ApiCodeConstant.AUTH_DOMAIN)) {
                oss_url = filename;
            } else {
                oss_url = FileUpOrDwUtil.uploadLocalFile(filename, type + "/" + UUID.randomUUID() + StringUtil.getFileName(filename));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String ret = "";
        if (filename.contains("http")) {
            ret = WxCurlUtil.postFileFromUrl(postUrl, filename);
        } else {
            ret = WxCurlUtil.postFile(postUrl, filename);
        }
        WxCurlUtil.ret(ret);
        JSONObject mediaJson = JSONObject.fromObject(ret);
        String mediaId = mediaJson.getString("media_id");
        MediaVo mediaVo = new MediaVo();
        mediaVo.setMediaId(mediaId);
        mediaVo.setUrl(oss_url);
        if (mediaJson.has("url")) {
            String wxUrl = mediaJson.getString("url");
            mediaVo.setWxUrl(wxUrl);

        }
        return mediaVo;
    }

    /**
     * 为上传图文的图片生成 thumb_media_id(这是临时的,艹)
     *
     * @param file
     * @return
     */
    public String uploadThumbMediaId(MultipartFile file, String appId) {
        //执行上传缩略图片方法
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=" + coreBiz.getToken(appId) + "&type=thumb";
        //String ret = WxCurlUtil..postFile(url, file);
        String ret = WxCurlUtil.postFile(url, "C:\\Users\\lenovo\\Desktop\\image\\example-crop.jpg");
        WxCurlUtil.ret(ret);
        JSONObject image_url = JSONObject.fromObject(ret);
        return image_url.getString("thumb_media_id");
    }

    /**
     * 为上传图文的图片生成 thumb_media_id
     *
     * @param filePath 文件地址
     * @return
     */
    public String uploadThumbMediaId(String filePath, String appId) {
        //执行上传缩略图片方法
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=" + coreBiz.getToken(appId) + "&type=thumb";
        String ret = WxCurlUtil.postFileFromUrl(url, filePath);
        WxCurlUtil.ret(ret);
        JSONObject image_url = JSONObject.fromObject(ret);
        return image_url.getString("thumb_media_id");
    }

    /**
     * 上传图片,获取图文内部访问的url地址
     *
     * @param file spring上传对象
     * @return
     */
    public MediaVo uploadImg(MultipartFile file, String appId) {
        //执行上传图片方法
        String url = Constants.BASEURL + "media/uploadimg?access_token=" + coreBiz.getToken(appId);
        String ret = WxCurlUtil.postFile(url, file);
        WxCurlUtil.ret(ret);
        JSONObject image_url = JSONObject.fromObject(ret);
        String wxUrl = image_url.getString("url");

        //存进阿里云
        String oss_url = null;
        try {
            oss_url = FileUpOrDwUtil.uploadNetStream(file.getInputStream(), "image/" + file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaVo vo = new MediaVo();
        vo.setUrl(oss_url);
        vo.setWxUrl(wxUrl);

        return vo;
    }

    /**
     * 上传图片,获取图文内部访问的url地址(只做测试用,返回可访问的url)
     *
     * @return
     */
    public MediaVo uploadImg(String filePathName, String appId) {
        //执行上传图片方法
        String url = Constants.BASEURL + "media/uploadimg?access_token=" + coreBiz.getToken(appId);
        //String ret = WxCurlUtil..postFile(url,"C:\\Users\\lenovo\\Desktop\\image\\haha.JPG");
        String ret = WxCurlUtil.postFile(url, filePathName);
        WxCurlUtil.ret(ret);
        JSONObject image_url = JSONObject.fromObject(ret);
        String wxUrl = image_url.getString("url");
        //存进阿里云
        String oss_url = null;
        try {
            oss_url = FileUpOrDwUtil.uploadLocalFile(filePathName, "image/" + StringUtil.getFileName(filePathName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaVo vo = new MediaVo();
        vo.setUrl(oss_url);
        vo.setWxUrl(wxUrl);
        return vo;
    }

    /**
     * 数据库增加图文信息(默认以第一条为首页)
     *
     * @param news
     * @param appId
     * @param userId
     */
    public int addNews(List<NewsVo> news, String appId, int userId) {
        TMaterialNews mater = new TMaterialNews();
        //公共属性
        mater.setAppid(appId);
        mater.setUserid(userId);
        mater.setCreatetime(new Date());
        mater.setUpdatetime(mater.getCreatetime());

        mater.setState(0);

        //插入第一个
        NewsVo firstArticle = news.get(0);

        dressMaterialNews(firstArticle, mater);
        mater.setCreatetime(new Date());
        materialNewsDao.insertSelective(mater);
        Integer newId = mater.getId();
        firstArticle.setId(newId);
        if (news.size() > 1) {
            //随后的子Article
            for (int i = 1; i < news.size(); i++) {
                TMaterialNews subNews = new TMaterialNews();
                subNews.setGroupid(newId);
                subNews.setAppid(appId);
                dressMaterialNews(news.get(i), subNews);
                subNews.setCreatetime(new Date());
                materialNewsDao.insertSelective(subNews);
                news.get(i).setId(subNews.getId());
            }
        }

        return newId;
    }

    /**
     * 辅助方法
     *
     * @param newsVo
     * @param mater
     */
    public void dressMaterialNews(NewsVo newsVo, TMaterialNews mater) {

        if (newsVo.getId() != null) {
            mater.setId(newsVo.getId());
        }

        if (newsVo.getTitle() != null) {
            mater.setTitle(newsVo.getTitle());
        }
        if (newsVo.getAuthor() != null) {
            mater.setAuthor(newsVo.getAuthor());
        }
        if (newsVo.isShowCover()) {
            mater.setShowcover(1);
        } else {
            mater.setShowcover(0);
        }
        if (newsVo.getIntro() != null) {
            mater.setIntro(newsVo.getIntro());
        } else {
            String content = newsVo.getContent();
            if (StringUtil.hasLength(content)) {
                content = StringUtil.replaceHtml(content);
                mater.setIntro(StringUtil.toCut(content, 54));
            }
        }
        if (newsVo.getContent() != null) {
            mater.setContent(newsVo.getContent());
        }
        if (newsVo.getLink() != null) {
            mater.setLink(newsVo.getLink());
        }
        if (newsVo.getThumbMediaId() != null) {
            mater.setThumbmediaid(newsVo.getThumbMediaId());
        }
        if (newsVo.getThumbUrl() != null) {
            mater.setThumbUrl(newsVo.getThumbUrl());
        }
        if (newsVo.getWxThumbUrl() != null) {
            mater.setWxThumbUrl(newsVo.getWxThumbUrl());
        }
        if (newsVo.getUrl() != null) {
            mater.setUrl(newsVo.getUrl());
        }
        if (newsVo.getMediaId() != null) {
            mater.setMediaid(newsVo.getMediaId());
        }
        if (newsVo.getAppId() != null) {
            mater.setAppid(newsVo.getAppId());
        }
    }

    /**
     * 数据库增加图片信息
     *
     * @param image
     */
    public int addImage(ImageVo image) {
        TMaterialImage tMaterialImage = new TMaterialImage();
        if (image.getMediaId() != null) {
            tMaterialImage.setMediaid(image.getMediaId());
        }
        if (image.getTitle() != null) {
            tMaterialImage.setTitle(image.getTitle());
        }
        if (image.getUrl() != null) {
            tMaterialImage.setUrl(image.getUrl());
        }
        if (image.getWxUrl() != null) {
            tMaterialImage.setWxUrl(image.getWxUrl());
        }
        if (image.getUserId() != null) {
            tMaterialImage.setUserid(image.getUserId());
        }
        if (image.getAppId() != null) {
            tMaterialImage.setAppid(image.getAppId());
        }
        if (image.isTemp()) {
            tMaterialImage.setIstemp(0);
        } else {
            tMaterialImage.setIstemp(1);
        }
        tMaterialImage.setCreatetime(new Date());
        tMaterialImage.setState(0);

        materialImageDao.insertSelective(tMaterialImage);

        return tMaterialImage.getId();
    }


    /**
     * 数据库记录其他类型文件
     *
     * @param file
     */
    public void addFile(FileVo file) {
        TMaterialFile info = new TMaterialFile();

        info.setAppid(file.getAppId());
        if (file.getMediaId() != null) {
            info.setMediaid(file.getMediaId());
        }
        if (file.getUrl() != null) {
            info.setUrl(file.getUrl());
        }
        if (file.getWxUrl() != null) {
            info.setWxUrl(file.getWxUrl());
        }
        if (file.isTemp()) {
            info.setIstemp(0);
        } else {
            info.setIstemp(1);
        }
        if (file.getType() != null) {
            info.setType(file.getType());
        }
        if (file.getIntroduction() != null) {
            info.setIntroduction(file.getIntroduction());
        }
        if (file.getUserId() != null) {
            info.setUrl(file.getUserId().toString());
        }

        info.setCreatetime(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(new Date()));
        info.setState(0);

        materialFileDao.insertSelective(info);
    }

    public void addText(TextVo text) {
        TMaterialText info = new TMaterialText();
        if (text.getAppId() != null) {
            info.setAppid(text.getAppId());
        }
        if (text.getContent() != null) {
            info.setContent(text.getContent());
        }
        if (text.getUserId() != null) {
            info.setUserid(text.getUserId());
        }
        info.setState(0);
        info.setCreatetime(new Date());
        info.setUpdatetime(info.getCreatetime());

        materialTextDao.insertSelective(info);
    }

    public void deleteNews(int id, String appId) {
        TMaterialNews tMaterialNews = materialNewsDao.selectById(id, appId);
        List<TMaterialNews> list = null;
        if (tMaterialNews != null) {
            list = materialNewsDao.selectSubNewsById(id);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(tMaterialNews);
        } else {
            throw new BusinessException("不存在对应的图文,删除图文失败");
        }

        //查询是否被自动回复或者菜单占用
        List<Integer> autos = replyAutoDao.selectMaterialByAppId(appId, MediaType.NEWS);
        List<Integer> menus = subscrMenuDao.selectMaters(appId, MediaType.NEWS, 0);

        if (autos != null && autos.size() > 0 && autos.get(0) != 0) {
            for (Integer value : autos) {
                for (TMaterialNews news : list) {
                    if (news.getId().equals(value)) {
                        throw new BusinessException("[" + news.getTitle() + "] 素材用于自动或关注回复,删除该图文,请先解除关系");
                    }
                }
            }
        }
        if (menus != null && menus.size() > 0 && menus.get(0) != 0) {
            for (Integer value : menus) {
                for (TMaterialNews news : list) {
                    if (news.getId().equals(value)) {
                        throw new BusinessException("[" + news.getTitle() + "] 素材用于公众号菜单,删除该图文,请先解除关系");
                    }
                }
            }
        }

        if (tMaterialNews != null) {
            //删除本地素材
            materialNewsDao.deleteByPrimaryKey(id);

            //删除微信素材
            String mediaid = tMaterialNews.getMediaid();

            queueSender.send("deleteNews.queue", mediaid + "," + appId);
        }
    }

    public void deleteImage(String ids, String appId) {
        List<Integer> intids = new ArrayList<Integer>();
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) {
            intids.add(Integer.parseInt(split[i]));
        }
        if (intids.size() <= 0) {
            throw new BusinessException("删除参数异常");
        }

        List<TMaterialImage> tMaterialImages = materialImageDao.selectMediaidById(intids, appId);
        if (tMaterialImages == null || tMaterialImages.size() <= 0) {
            throw new BusinessException("删除,不存在此图片");
        }

        //查询是否被自动回复或者菜单占用
        List<Integer> autos = replyAutoDao.selectMaterialByAppId(appId, MediaType.IMG);
        List<Integer> menus = subscrMenuDao.selectMaters(appId, MediaType.IMG, 0);

        if (autos != null && autos.size() > 0 && autos.get(0) != 0) {
            for (Integer value : autos) {
                for (Integer id : intids) {
                    if (id.equals(value)) {
                        throw new BusinessException("图片素材用于自动或关注回复,删除该图片,请先解除关系");
                    }
                }
            }
        }
        if (menus != null || menus.size() > 0 && menus.get(0) != 0) {
            for (Integer value : menus) {
                for (TMaterialImage image : tMaterialImages) {
                    if (image.getId().equals(value)) {
                        throw new BusinessException("[" + image.getTitle() + "] 素材用于公众号菜单,删除该图片,请先解除关系");
                    }
                }
            }
        }

        //删除本地素材
        materialImageDao.deleteBacthById(intids, appId);

        //异步删除
        StringBuffer idsAndAppId = new StringBuffer();
        for (TMaterialImage me : tMaterialImages) {
            idsAndAppId.append(me.getMediaid() + ",");
        }
        idsAndAppId.append(appId);
        queueSender.send("deleteImage.queue", idsAndAppId.toString());
    }

    public void delFile(int id, String appId) {
        TMaterialFile tMaterialFile = materialFileDao.selectByPrimaryKey(id);
        //删除微信素材
        String jsonStr = "{" + "\"media_id\":" + "\"" + tMaterialFile.getMediaid() + "\"" + "}";
        String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "material/del_material?access_token=" + coreBiz.getToken(appId), jsonStr);
        WxCurlUtil.ret(ret);
        //删除本地素材
        materialFileDao.deleteByPrimaryKey(id);
    }

    public void delText(int id) {
        materialTextDao.deleteByPrimaryKey(id);
    }

    /**
     * 更新微信服务器,更新本地
     *
     * @param news
     * @param userId
     * @param id
     */
    public void updateNews(List<NewsVo> news, String appId, String userId, int id) {
        //直接删除原先的,擦
        deleteNews(id, appId);

        //新增微信
        List<NewsVo> list = uploadNews(news, appId);

        //增加进数据库
        addNews(list, appId, Integer.parseInt(userId));


    }

    public void updateText(TextVo text) {
        TMaterialText tMaterialText = new TMaterialText();
        tMaterialText.setId(text.getId());
        tMaterialText.setUpdatetime(new Date());
        if (text.getUserId() != null) {
            tMaterialText.setUserid(text.getUserId());
        }
        if (text.getContent() != null) {
            tMaterialText.setContent(text.getContent());
        }
        if (text.getAppId() != null) {
            tMaterialText.setAppid(text.getAppId());
        }
        materialTextDao.updateByPrimaryKeyWithBLOBs(tMaterialText);
    }

    /**
     * 查询
     *
     * @param appId
     * @param type
     * @return
     */
    public List getMaters(String appId, String type) {

        if (type.equals(MediaType.TEXT)) {
            return materialTextDao.selectList(appId);
        } else if (type.equals(MediaType.NEWS)) {
            return materialNewsDao.selectList(appId);
        } else if (type.equals(MediaType.IMG)) {
            return materialImageDao.selectList(appId);
        } else if (type.equals(MediaType.NUSIC) || type.equals(MediaType.VIDEO) || type.equals(MediaType.VOICE)) {
            return materialFileDao.selectList(appId);
        }
        return null;
    }

    public PageInfo<ImageVo> getImageMaters(int page, int limit, String appid) {
        PageHelper.startPage(page, limit, "createTime desc");
        List<TMaterialImage> list = materialImageDao.selectList(appid);
        PageModel<ImageVo> imageVos = new PageModel<ImageVo>((Page) list);

        for (TMaterialImage item : list) {
            ImageVo vo = new ImageVo();
            vo.setId(item.getId());
            vo.setTitle(item.getTitle());
            vo.setMediaId(item.getMediaid());
            vo.setUrl(item.getUrl());
            imageVos.add(vo);
        }
        return new PageInfo<>(imageVos);
    }

    public List<FileVo> getFileMaters(String appId) {

        List<FileVo> fileVos = new ArrayList<FileVo>();
        List<TMaterialFile> list = materialFileDao.selectList(appId);
        for (TMaterialFile item : list) {
            FileVo vo = new FileVo();
            vo.setAppId(item.getAppid());
            vo.setIntroduction(item.getIntroduction());
            vo.setMediaId(item.getMediaid());
            vo.setTitle(item.getTitle());
            vo.setUrl(item.getUrl());
            vo.setWxUrl(item.getWxUrl());
            if (item.getIstemp() == 0) {
                vo.setTemp(true);
            } else {
                vo.setTemp(false);
            }
            vo.setType(item.getType());
            vo.setUserId(item.getUserid());
            fileVos.add(vo);
        }
        return fileVos;
    }

    public List<TextVo> getTextMaters(String appId) {
        List<TextVo> textVos = new ArrayList<TextVo>();
        List<TMaterialText> list = materialTextDao.selectList(appId);
        for (TMaterialText item : list) {
            TextVo vo = new TextVo();
            vo.setAppId(item.getAppid());
            vo.setContent(item.getContent());
            vo.setId(item.getId());
            vo.setUserId(item.getUserid());
            textVos.add(vo);
        }
        return textVos;
    }

    public PageInfo<NewsTreeVo> getNewsList(String appId, int page, int limit) {
        PageHelper.startPage(page, limit, "createTime desc");
        List<TMaterialNews> list = materialNewsDao.selectList(appId);

        PageModel<NewsTreeVo> newsResVos = new PageModel<NewsTreeVo>((Page) list);

        for (TMaterialNews item : list) {
            NewsTreeVo newsResVo = new NewsTreeVo();
            newsResVo.setNews(getNewsReVo(item));

            List<NewsVo> news = getSubNews(item.getId());
            newsResVo.setSubNews(news);

            newsResVos.add(newsResVo);
        }

        return new PageInfo<NewsTreeVo>(newsResVos);
    }

    public List<NewsVo> getSubNews(Integer id) {
        List<TMaterialNews> list = materialNewsDao.selectSubNewsById(id);
        List<NewsVo> newsResVos = new ArrayList<NewsVo>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                newsResVos.add(getNewsReVo(list.get(i)));
            }
        }
        return newsResVos;
    }

    public NewsTreeVo getNews(Integer id, String appId) {
        NewsTreeVo treeVo = new NewsTreeVo();
        TMaterialNews first = materialNewsDao.selectById(id, appId);
        if (first == null) {
            return null;
        }
        treeVo.setNews(this.getNewsReVo(first));
        if(first.getGroupid() != null && first.getGroupid() == 0){
            List<NewsVo> news = this.getSubNews(id);
            treeVo.setSubNews(news);
        }

        return treeVo;
    }

    public PageInfo<SysNewsTreeVo> getSysNewsList(String appId, int page, int limit, int type) {
        PageHelper.startPage(page, limit);
        List<TMaterialSysnews> list;
        if(type==0)
            list = materialSysnewsDao.selectList(appId);
        else
            list = materialSysnewsDao.selectSysList(appId);
        if (list == null) {
            return new PageInfo<>();
        }
        PageModel<SysNewsTreeVo> newsResVos = new PageModel<>((Page) list);

        for (TMaterialSysnews item : list) {
            SysNewsTreeVo newsResVo = new SysNewsTreeVo();
            newsResVo.setNews(getSysNewsReVo(item));

            List<SysnewsVo> news = getSubSysNews(item.getId());
            newsResVo.setSubNews(news);

            newsResVos.add(newsResVo);
        }

        return new PageInfo<>(newsResVos);
    }

    public List<SysnewsVo> getSubSysNews(Integer id) {
        List<TMaterialSysnews> list = materialSysnewsDao.selectSubSysNewsById(id);
        List<SysnewsVo> newsResVos = new ArrayList<SysnewsVo>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                newsResVos.add(getSysNewsReVo(list.get(i)));
            }
        }
        return newsResVos;
    }


    public SysNewsTreeVo getSysnews(Integer id, String appId) {
        TMaterialSysnews first = materialSysnewsDao.selectById(id, appId);
        if (first == null) {
            return null;
        }
        SysNewsTreeVo treeVo = new SysNewsTreeVo();
        treeVo.setNews(getSysNewsReVo(first));
        if(first.getGroupid() != null && first.getGroupid() == 0){
            treeVo.setSubNews(getSubSysNews(id));
        }

        return treeVo;
    }


    public ImageVo getImg(int id, String appId) {
        TMaterialImage item = materialImageDao.select(id, appId);
        if (item == null) {
            return null;
        }
        ImageVo vo = new ImageVo();
        vo.setId(item.getId());
        vo.setTitle(item.getTitle());
        vo.setMediaId(item.getMediaid());
        vo.setUrl(item.getUrl());
        return vo;
    }

    public void updateImg(int id, String title, String appId) {
        TMaterialImage image = new TMaterialImage();
        image.setTitle(title);
        image.setId(id);
        image.setAppid(appId);
        materialImageDao.updateByPrimaryKeySelective(image);
    }

    @Override
    public void deleteImageForWX(List<String> ids, String appId) {
        for (String mediaid : ids) {
            String jsonStr = "{" + "\"media_id\":" + "\"" + mediaid + "\"" + "}";
            String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "material/del_material?access_token=" + coreBiz.getToken(appId), jsonStr);
            WxCurlUtil.ret(ret);
        }
    }

    @Override
    public void doSyncImage(String appId) {
        int totalCount = getMaterialCount(appId, "image_count");
        int count = totalCount % 20 == 0 ? totalCount / 20 : totalCount / 20 + 1;
        int offset = 0;
        JSONArray array;
        JSONObject obj;
        String ret;
        String oss_url;
        String jsonstr;
        TMaterialImage image;
        String token = coreBiz.getToken(appId);
        try {
            while (count > 0) {
                jsonstr = "{\"type\":\"image\",\"offset\":" + offset + ",\"count\":20}";
                ret = HttpConnUtil.doPostJson(Constants.BASEURL + "material/batchget_material?access_token=" + token, jsonstr);
                WxCurlUtil.ret(ret);
                array = JsonUtils.getJsonObject(ret).getJSONArray("item");
                if (array.size() > 0) {
                    for (Object object : array) {
                        obj = JsonUtils.getJsonObject(object.toString());
                        image = materialImageDao.selectMaterialByMediaId(obj.getString("media_id"), appId);
                        if (image == null) {
                            image = new TMaterialImage();
                            image.setAppid(appId);
                            image.setMediaid(obj.getString("media_id"));
                            image.setCreatetime(new Date());
                            image.setWxUrl(obj.getString("url"));
                            image.setState(0);
                            image.setIstemp(1);
                            image.setTitle(obj.getString("name"));
                            oss_url = FileUpOrDwUtil.uploadNetStream(WxCurlUtil.downloadPerpMaterial(token, image.getMediaid()), "jpg/" + UUID.randomUUID().toString() + ".jpg");
                            image.setUrl(oss_url);
                            materialImageDao.insertSelective(image);
                        }
                    }
                }
                offset += 20;
                count--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doSyncNews(String appId) {
        int totalCount = getMaterialCount(appId, "news_count");
        System.out.println("共有" + totalCount + "条数据");
        int count = totalCount % 20 == 0 ? totalCount / 20 : totalCount / 20 + 1;
        int offset = 0;
        TMaterialNews news;
        Integer pid;
        JSONArray array1;
        JSONArray array2;
        JSONObject obj1;
        JSONObject obj2;
        String ret;
        String jsonstr;
        String mediaId;
        String oss_url;
        String token = coreBiz.getToken(appId);
        List<TMaterialNews> list = new ArrayList<>();
        try {
            while (count > 0) {
                jsonstr = "{\"type\":\"news\",\"offset\":" + offset + ",\"count\":20}";
                ret = HttpConnUtil.doPostJson(Constants.BASEURL + "material/batchget_material?access_token=" + token, jsonstr);
                WxCurlUtil.ret(ret);
                array1 = JsonUtils.getJsonObject(ret).getJSONArray("item");
                if (array1.size() > 0) {
                    for (Object object1 : array1) {
                        obj1 = JsonUtils.getJsonObject(object1.toString());
                        mediaId = obj1.getString("media_id");
                        array2 = JsonUtils.getJsonObject(obj1.getString("content")).getJSONArray("news_item");
                        if (array2.size() > 0) {
                            for (Object object2 : array2) {
                                list = materialNewsDao.selectMaterialByMediaId(mediaId, appId);
                                obj2 = JsonUtils.getJsonObject(object2.toString());
                                if (list.size() == 0) {
                                    pid = 0;
                                } else {
                                    pid = list.get(0).getId();
                                }
                                news = materialNewsDao.selectMaterialByThumbMediaId(obj2.getString("thumb_media_id"), mediaId, appId);
                                if (news == null) {
                                    news = new TMaterialNews();
                                    news.setState(0);
                                    news.setAppid(appId);
                                    news.setGroupid(pid);
                                    news.setMediaid(mediaId);
                                    news.setCreatetime(new Date());
                                    news.setUrl(obj2.getString("url"));
                                    news.setTitle(obj2.getString("title"));
                                    news.setIntro(obj2.getString("digest"));
                                    news.setAuthor(obj2.getString("author"));
                                    news.setContent(obj2.getString("content"));
                                    news.setShowcover(obj2.getInt("show_cover_pic"));
                                    news.setThumbmediaid(obj2.getString("thumb_media_id"));
                                    news.setLink(obj2.getString("content_source_url"));
                                    oss_url = FileUpOrDwUtil.uploadNetStream(WxCurlUtil.downloadPerpMaterial(token, news.getThumbmediaid()), "thumb/" + UUID.randomUUID().toString() + ".jpg");
                                    news.setThumbUrl(oss_url);
                                    news.setWxThumbUrl(oss_url);
                                    materialNewsDao.insertSelective(news);
                                }
                            }
                        }
                    }
                }
                offset += 20;
                count--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取素材数量
     *
     * @param appId
     * @param type
     * @return
     */
    private int getMaterialCount(String appId, String type) {
        String response = HttpConnUtil.doHttpRequest(Constants.BASEURL + "material/get_materialcount?access_token=" + coreBiz.getToken(appId));
        WxCurlUtil.ret(response);
        JSONObject object = JsonUtils.getJsonObject(response);
        if (object != null) {
            return (int) object.get(type);
        } else {
            return 0;
        }
    }


    public int addSysnews(List<SysnewsVo> acticles, String appId) {
        Date createTime = new Date();
        //插入首个
        SysnewsVo vo = acticles.get(0);
        TMaterialSysnews model = addressActicle(vo, appId);
        model.setCreatetime(createTime);
        model.setType(1);
        model.setState(0);
        materialSysnewsDao.insertSelective(model);
        //如果有子图文
        Integer groupId = model.getId();
        if (acticles.size() > 1) {
            for (int i = 1; i < acticles.size(); i++) {
                TMaterialSysnews subModel = addressActicle(acticles.get(i), appId);
                subModel.setType(1);
                subModel.setGroupid(groupId);
                subModel.setState(0);
                subModel.setCreatetime(createTime);
                materialSysnewsDao.insertSelective(subModel);
            }
        }
        return groupId;
    }

    private TMaterialSysnews addressActicle(SysnewsVo vo, String appId) {
        TMaterialSysnews model = new TMaterialSysnews();
        model.setAppid(appId);
        model.setDescription(vo.getDescription());
        model.setPicurl(vo.getPicUrl());
        model.setUrl(vo.getUrl());
        model.setTitle(vo.getTitle());
        return model;
    }

    public void saveSysnews(List<SysnewsVo> acticles, Integer id, String appId) {
        materialSysnewsDao.deleteNewsById(id);
        this.addSysnews(acticles, appId);
    }

    @Override
    public void saveTran() {
        TMaterialText materialText = new TMaterialText();
        materialText.setAppid("sys");
        materialText.setContent("测试2");
        materialText.setUpdatetime(new Date());
        materialText.setUserid(0);
        materialText.setCreatetime(new Date());
        materialTextDao.insertSelective(materialText);
        throw new BusinessException("测试异常");
    }

    public SysnewsVo getSysNewsReVo(TMaterialSysnews item) {
        SysnewsVo vo = new SysnewsVo();

        vo.setId(item.getId());
        vo.setTitle(item.getTitle());
        vo.setUrl(item.getUrl());
        vo.setPicUrl(item.getPicurl());
        vo.setDescription(item.getDescription());
        vo.setCreateTime(item.getCreatetime());

        return vo;
    }

    public NewsVo getNewsReVo(TMaterialNews item) {
        NewsVo vo = new NewsVo();
        vo.setId(item.getId());
        vo.setTitle(item.getTitle());
        vo.setAuthor(item.getAuthor());
        vo.setContent(item.getContent());
        vo.setLink(item.getLink());
        vo.setIntro(item.getIntro());
        vo.setThumbUrl(item.getThumbUrl());
        vo.setWxThumbUrl(item.getWxThumbUrl());
        vo.setUrl(item.getUrl());
        vo.setThumbMediaId(item.getThumbmediaid());
        vo.setCreateTime(item.getCreatetime());
        vo.setMediaId(item.getMediaid());
        return vo;
    }

    @Override
    public Integer getMaterByMaterialId(String appId, String mediaId, String type) {
        if (type.equals(MediaType.NEWS)) {
            List<TMaterialNews> list = materialNewsDao.selectMaterialByMediaId(mediaId, appId);
            return list.size() == 0 ? null : list.get(0).getId();
        } else if (type.equals(MediaType.IMG)) {
            TMaterialImage image = materialImageDao.selectMaterialByMediaId(mediaId, appId);
            return image == null ? null : image.getId();
        } else if (type.equals(MediaType.NUSIC) || type.equals(MediaType.VIDEO) || type.equals(MediaType.VOICE)) {
            TMaterialFile file = materialFileDao.selectMaterialByMediaId(mediaId, appId);
            return file == null ? null : file.getId();
        }
        return null;
    }

    @Override
    public String uploadNewsImg(SerializeMultipartFile file, String appId) {
        String uuidName = UUID.randomUUID().toString();
        String exName = FileUpOrDwUtil.getExtensionName(file.getOriginalFilename());
        File tempFile = null;
        FileOutputStream stream = null;
        BufferedOutputStream bufferedOutput = null;
        try {
            tempFile = File.createTempFile(uuidName, "." + exName);
            stream = new FileOutputStream(tempFile);
            bufferedOutput = new BufferedOutputStream(stream);
            bufferedOutput.write(file.getBytes());
            bufferedOutput.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tempFile.deleteOnExit();
        }
        String postUrl = Constants.BASEURL + "media/uploadimg?access_token=" + coreBiz.getToken(appId);
        String ret = WxCurlUtil.postFile(postUrl, tempFile);
        WxCurlUtil.ret(ret);
        JSONObject mediaJson = JSONObject.fromObject(ret);
        return mediaJson.getString("url");
    }
}
