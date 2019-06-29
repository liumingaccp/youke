package youke.service.wx.biz.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.util.Constants;
import youke.facade.wx.vo.message.Article;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.biz.IMassMessageBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/10
 * Time: 12:15
 */
@Service
public class MassMessageBiz implements IMassMessageBiz{
    @Resource
    private ICoreBiz coreBiz;

    private String url = Constants.BASEURL + "message/mass/send?access_token=";

    public String sendText(String appId, String openId, String content) {
        String jsonParam = "{\n" +
                "    \"touser\":["+ openId +"],\n" +
                "    \"msgtype\":\"text\",\n" +
                "    \"text\":\n" +
                "    {\n" +
                "    \"content\":\""+ content +"\"\n" +
                "    }\n" +
                "}";
        String ret = HttpConnUtil.doPostJson(url + coreBiz.getToken(appId), jsonParam);
        WxCurlUtil.ret(ret);
        return ret;
    }

    public String sendImage(String appId, String openId, String mediaId) {
        String jsonParam = "{\n" +
                "    \"touser\":["+ openId +"],\n" +
                "    \"msgtype\":\"image\",\n" +
                "    \"image\":\n" +
                "    {\n" +
                "      \"media_id\":\""+ mediaId +"\"\n" +
                "    }\n" +
                "}";
        String ret = HttpConnUtil.doPostJson(url + coreBiz.getToken(appId), jsonParam);
        WxCurlUtil.ret(ret);
        return ret;
    }

    public String sendVoice(String appId, String openId, String mediaId) {
        String jsonParam = "{\n" +
                "    \"touser\":["+ openId +"],\n" +
                "    \"msgtype\":\"voice\",\n" +
                "    \"voice\":\n" +
                "    {\n" +
                "      \"media_id\":\""+ mediaId +"\"\n" +
                "    }\n" +
                "}";
        String ret = HttpConnUtil.doPostJson(url + coreBiz.getToken(appId), jsonParam);
        WxCurlUtil.ret(ret);
        return ret;
    }

    public String sendVideo(String appId, String openId, String mediaId,String thumb_media_id, String title, String desc) {
        String jsonParam = "{\n" +
                "    \"touser\":["+ openId +"],\n" +
                "    \"msgtype\":\"video\",\n" +
                "    \"video\":\n" +
                "    {\n" +
                "      \"media_id\":\""+ mediaId +"\",\n" +
                "      \"title\":\""+ title +"\",\n" +
                "      \"description\":\""+ desc +"\"\n" +
                "    }\n" +
                "}";
        String ret = HttpConnUtil.doPostJson(url + coreBiz.getToken(appId), jsonParam);
        WxCurlUtil.ret(ret);
        return ret;
    }

    public String sendMusic(String appId, String openId, String thumbMediaId, String title, String desc, String musicurl, String hqmusicurl) {
        String jsonParam = "{\n" +
                "    \"touser\":["+ openId +"],\n" +
                "    \"msgtype\":\"music\",\n" +
                "    \"music\":\n" +
                "    {\n" +
                "      \"title\":\""+ title +"\",\n" +
                "      \"description\":\""+ desc +"\",\n" +
                "      \"musicurl\":\""+ musicurl +"\",\n" +
                "      \"hqmusicurl\":\""+ hqmusicurl +"\",\n" +
                "      \"thumb_media_id\":\""+ thumbMediaId +"\" \n" +
                "    }\n" +
                "}";
        String ret = HttpConnUtil.doPostJson(url + coreBiz.getToken(appId), jsonParam);
        WxCurlUtil.ret(ret);
        return ret;
    }

    public String sendNews(String appId, String openId, List<Article> articles) {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for(Article acticle : articles){
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", acticle.getTitle());
            map.put("description", acticle.getDescription());
            map.put("url", acticle.getUrl());
            map.put("picurl", acticle.getPicUrl());
            list.add(map);
        }

        String jsonParam = "{\n" +
                "    \"touser\":["+ openId +"],\n" +
                "    \"msgtype\":\"news\",\n" +
                "    \"news\":{\n" +
                "        \"articles\": [\n" + JSON.toJSONString(list) +
                "         ]\n" +
                "    }\n" +
                "}";
        String ret = HttpConnUtil.doPostJson(url + coreBiz.getToken(appId), jsonParam);
        WxCurlUtil.ret(ret);
        return ret;
    }

    public String sendNews(String appId, String openId, String mediaId) {
        String jsonParam = "{\n" +
                "    \"touser\":["+ openId +"],\n" +
                "    \"msgtype\":\"mpnews\",\n" +
                "    \"mpnews\":\n" +
                "    {\n" +
                "         \"media_id\":\""+ mediaId +"\"\n" +
                "    }\n" +
                "}";
        String ret = HttpConnUtil.doPostJson(url + coreBiz.getToken(appId), jsonParam);
        WxCurlUtil.ret(ret);
        return ret;
    }

    public String getVideoMediaId(String mediaId, String title, String desc, String appId) {
        String jsonParam = "{\n" +
                "  \"media_id\": \"" + mediaId + "\",\n" +
                "  \"title\": \"" + title + "\",\n" +
                "  \"description\": \"" + desc + "\"\n" +
                "}";
        String ret = HttpConnUtil.doPostJson(url + coreBiz.getToken(appId), jsonParam);
        WxCurlUtil.ret(ret);

        return ret;
    }
}
