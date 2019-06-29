package youke.service.wx.provider;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.dao.ITagDao;
import youke.common.model.TTag;
import youke.facade.wx.provider.IWeixinFansService;
import youke.facade.wx.vo.fans.FansVo;
import youke.service.wx.biz.IFansBiz;
import youke.service.wx.biz.ITagBiz;
import youke.service.wx.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 19:02
 */
@Service
public class WeixinFansService implements IWeixinFansService {

    @Resource
    private IFansBiz fansBiz;
    @Resource
    private ITagBiz tagBiz;
    @Resource
    private ITagDao tagDao;
    @Resource
    private QueueSender queueSender;

    public void saveFans(FansVo fans) {
        fansBiz.saveFans(fans);
    }

    public void saveFans(String openId, String appId) {
        fansBiz.saveFans(openId,appId);
    }

    public void updateFansInBlack(String openId, String appId) {
        fansBiz.updateFansInBlack(openId, appId);
    }

    public void updateFansOutBlack(String openId, String appId) {
        fansBiz.updateFansOutBlack(openId, appId);
    }

    public void updateFansInBlack(List<String> openIds, String appId) {
        fansBiz.updateFansInBlack(openIds, appId);
    }

    public void updateFansOutBlack(List<String> openIds, String appId) {
        fansBiz.updateFansOutBlack(openIds, appId);
    }
    public void upTags(String appId) {
        //发送后台消息队列
        queueSender.send("uploadFansTags.queue", appId);
    }

    public void downTags(String appId) {
        queueSender.send("downloadFansTags.queue", appId);
    }

    public void deleteOpenIdTags(String openId, String tags, String appId) {
        List<String> openIds = new ArrayList<String>();
        openIds.add(openId);
        String[] split = tags.split(",");
        if(split != null){
            if(split.length == 1){
                TTag tTag = tagDao.selectByPrimaryKey(Integer.parseInt(split[0]));
                if(tTag != null && tTag.getWxtagid() != null){
                    tagBiz.batchuntagging(openIds, Integer.parseInt(tTag.getWxtagid()), appId);
                }
            }else{
                for(int i=0; i< split.length; i++){
                    TTag tTag = tagDao.selectByPrimaryKey(Integer.parseInt(split[0]));
                    if(tTag != null && tTag.getWxtagid() != null ){
                        tagBiz.batchuntagging(openIds, Integer.parseInt(tTag.getWxtagid()), appId);
                    }
                }
            }
        }

    }

    public void deleteTag(int tagId, String appId) {

        List<String> wxOpenIds = new ArrayList<>();
        //查看当前的粉丝数量
        String ret = tagBiz.getOpensByTagId(tagId, appId, null);
        JSONObject jsonObject = JSONObject.fromObject(ret);
        if(jsonObject.containsKey("count")) {
            int count = jsonObject.getInt("count");
            if (count > 0) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray openid = data.getJSONArray("openid");
                for (int i = 0; i < openid.size(); i++) {
                    wxOpenIds.add(openid.getString(i));
                }
                tagBiz.batchuntagging(wxOpenIds, tagId, appId);
            }
        }
        tagBiz.delete(tagId, appId);
    }
}
