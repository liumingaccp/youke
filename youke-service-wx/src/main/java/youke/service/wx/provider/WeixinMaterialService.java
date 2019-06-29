package youke.service.wx.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.dao.IMaterialNewsDao;
import youke.common.exception.BusinessException;
import youke.common.model.TMaterialNews;
import youke.common.model.vo.file.SerializeMultipartFile;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.provider.IWeixinMaterialService;
import youke.facade.wx.util.Constants;
import youke.facade.wx.vo.MediaVo;
import youke.facade.wx.vo.material.*;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.biz.IMaterialBiz;
import youke.service.wx.biz.IMenuBiz;
import youke.service.wx.biz.IReplyBiz;
import youke.service.wx.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/6
 * Time: 10:23
 */
@Service
public class WeixinMaterialService implements IWeixinMaterialService {

    @Resource
    private IMaterialBiz materialBiz;
    @Resource
    private ICoreBiz coreBiz;
    @Resource
    private IMaterialNewsDao materialNewsDao;

    public int addImg(SerializeMultipartFile file, Integer userId, String appId) {
        MediaVo mediaVo = materialBiz.uploadMaterial(file, appId);
        if(mediaVo == null){
            return 0;
        }
        ImageVo vo = new ImageVo();
        vo.setWxUrl(mediaVo.getWxUrl());
        vo.setUserId(userId);
        vo.setUrl(mediaVo.getUrl());
        vo.setMediaId(mediaVo.getMediaId());
        vo.setAppId(appId);
        vo.setTitle(file.getOriginalFilename());
        vo.setTemp(false);

        return materialBiz.addImage(vo);

    }

    public void deleteImg(String ids, String appId) {
        if(ids == null){
            throw new BusinessException("请提供正确的可删除图片从参数");
        }
        try {
            String[] split = ids.split(",");
        }catch (Exception e){
            throw new BusinessException("删除图片素材参数错误");
        }
        materialBiz.deleteImage(ids, appId);
    }

    @Override
    public void deleteImgForWX(List<String> ids, String appId) {
        materialBiz.deleteImageForWX(ids, appId);
    }

    public PageInfo<ImageVo> getImgList(int page, int limit, String appId) {
        return materialBiz.getImageMaters(page, limit, appId);
    }

    public ImageVo getImg(int id, String appId) {
        return materialBiz.getImg(id, appId);
    }

    public void updateImgTitle(int id, String title, String appId) {
        materialBiz.updateImg(id, title, appId);
    }

    public PageInfo<NewsTreeVo> getNewsList(int page, int limit, String appId) {
        return materialBiz.getNewsList(appId, page, limit);
    }

    public NewsTreeVo getNews(int id, String appId) {
        return materialBiz.getNews(id, appId);
    }

    public int addNews(List<NewsVo> news, String appId, Integer userId, Integer id) {

        if(news == null || news.size() <= 0){
            throw new BusinessException("请填写保存的图文内容");
        }

        if(id != null && id > 0){
            //删除原先的
            this.delNews(id, appId);
        }

        /*//判断封面的图片是否是临时的素材
        for(NewsVo newsVo : news){
            //设置appId
            newsVo.setAppId(appId);
            //如果是临时素材,要上传至永久素材
            if(newsVo.getThumbMediaId() == null || "".equals(newsVo.getThumbMediaId())){
                MediaVo mediaVo = materialBiz.uploadMaterial(newsVo.getThumbUrl(), appId);
                ImageVo imageVo = new ImageVo();
                imageVo.setAppId(appId);
                imageVo.setWxUrl(mediaVo.getWxUrl());
                imageVo.setUrl(mediaVo.getUrl());
                imageVo.setMediaId(mediaVo.getMediaId());
                materialBiz.addImage(imageVo);
                newsVo.setThumbUrl(mediaVo.getUrl());
                newsVo.setWxThumbUrl(mediaVo.getWxUrl());
                newsVo.setMediaId(mediaVo.getMediaId());
            }
        }*/

        List<NewsVo> list = materialBiz.uploadNews(news, appId);

        int i = materialBiz.addNews(list, appId, userId);

        return i;
    }

    public void delNews(int id, String appId) {
        materialBiz.deleteNews(id, appId);
    }

    public SysNewsTreeVo getSysnews(int id, String appId) {
        return materialBiz.getSysnews(id, appId);
    }

    public PageInfo<SysNewsTreeVo> getSysnewsList(int page, int limit, String appId) {
        return this.querySysnewsList(page, limit, appId, 0);
    }

    public int addOrSaveSysnews(List<SysnewsVo> acticles, Integer id, String appId) {
        if(acticles == null || acticles.size() <= 0){
            new BusinessException("请填写保存的系统图文");
        }
        if(id == null){
            return materialBiz.addSysnews(acticles, appId);
        }else{
            materialBiz.saveSysnews(acticles, id, appId);
            return 0;
        }
    }

    public PageInfo<SysNewsTreeVo> getMarketNewsList(int page, int limit, String appId) {
        return querySysnewsList(page, limit, appId, 1);
    }

    public PageInfo<SysNewsTreeVo> querySysnewsList(int page, int limit, String appId, int type) {
        return materialBiz.getSysNewsList(appId, page, limit, type);
    }

    @Override
    public void deleteNewsForWX(String mediaId, String appId) {
        String jsonStr =  "{" + "\"media_id\":" + "\"" + mediaId + "\"" + "}";
        String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "material/del_material?access_token=" + coreBiz.getToken(appId), jsonStr);
        WxCurlUtil.ret(ret);
    }

    @Override
    public void addNewsForWx(String appId, List<NewsVo> news) {
        List<NewsVo> list = materialBiz.uploadNews(news, appId);
        //修改本地的图文
        if(list != null && list.size() > 0){
            for(NewsVo vo : list){
                TMaterialNews model = new TMaterialNews();
                model.setId(vo.getId());
                model.setMediaid(vo.getMediaId());
                model.setUrl(vo.getUrl());
                materialNewsDao.updateByPrimaryKeySelective(model);
            }
        }

    }
}
