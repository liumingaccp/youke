package youke.service.wx.biz.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.util.Constants;
import youke.facade.wx.vo.tag.TagVo;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.biz.ITagBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/12
 * Time: 16:48
 */
@Service
public class TagBiz implements ITagBiz {
    @Resource
    private ICoreBiz coreBiz;
    public String createTag(String tagName, String appId) {
        String url = Constants.BASEURL + "tags/create?access_token=" + coreBiz.getToken(appId);
        String param = "{\"tag\" : {\"name\" : \""+ tagName +"\"}}";
        String ret = HttpConnUtil.doPostJson(url, param);
        WxCurlUtil.ret(ret);
        return ret;
    }

    public List<TagVo> getAllTag(String appId) {
        String url  = Constants.BASEURL + "tags/get?access_token=" + coreBiz.getToken(appId);
        String ret = HttpConnUtil.doHttpRequest(url);
        WxCurlUtil.ret(ret);
        JSONObject jsonObject = JSONObject.fromObject(ret);
        JSONArray tags = jsonObject.getJSONArray("tags");
        List<TagVo> tagVos = new ArrayList<TagVo>();
        for(int i=0; i<tags.size(); i++){
            TagVo tagVo = new TagVo();
            JSONObject jsonTag = tags.getJSONObject(i);
            tagVo.setId(jsonTag.getInt("id"));
            tagVo.setCount(jsonTag.getInt("count"));
            tagVo.setName(jsonTag.getString("name"));
            tagVos.add(tagVo);
        }

        return tagVos;
    }

    public void updateTag(int tagId, String tagName, String appId) {
        String url = Constants.BASEURL + "tags/update?access_token=" + coreBiz.getToken(appId);
        String param = "{\"tag\" : {\"id\" : "+  tagId +",  \"name\" : \""+ tagName +"\"}}";
        String ret = HttpConnUtil.doPostJson(url, param);
        WxCurlUtil.ret(ret);
    }

    public void delete(int id, String appId) {
        String url = Constants.BASEURL + "tags/delete?access_token=" + coreBiz.getToken(appId);
        String param = "{\"tag\" : {\"id\" : "+ id +"}";
        String ret = HttpConnUtil.doPostJson(url, param);
        WxCurlUtil.ret(ret);
    }

    public void batchTagging(List<String> openIds, int tagId, String appId) {
        String url = Constants.BASEURL + "tags/members/batchtagging?access_token=" + coreBiz.getToken(appId);
        StringBuffer param = new StringBuffer();
        param.append("{\"openid_list\" : [");
        for(String str : openIds){
            param.append("\"" + str + "\",");
        }
        param.deleteCharAt(param.length() - 1);
        param.append("],").append("\"tagid\" : "+ tagId +"}");
        String ret = HttpConnUtil.doPostJson(url, param.toString());
        WxCurlUtil.ret(ret);
    }

    public void batchuntagging(List<String> openIds, int tagId, String appId) {
        String url = Constants.BASEURL + "tags/members/batchuntagging?access_token=" + coreBiz.getToken(appId);
        StringBuffer param = new StringBuffer();
        param.append("{\"openid_list\" : [");
        for(String str : openIds){
            param.append("\"" + str + "\",");
        }
        param.deleteCharAt(param.length() - 1);
        param.append("],").append("\"tagid\" : "+ tagId +"}");
        System.err.println(param);
        String ret = HttpConnUtil.doPostJson(url, param.toString());
        WxCurlUtil.ret(ret);
    }

    public List<Integer> getTagIdList(String openId, String appId) {
        String url = Constants.BASEURL + "tags/getidlist?access_token=" + coreBiz.getToken(appId);
        String param = "{   \"openid\" : \""+ openId +"\" }";
        String ret = HttpConnUtil.doPostJson(url, param);
        WxCurlUtil.ret(ret);
        JSONObject jsonObject = JSONObject.fromObject(ret);
        JSONArray tagidList = jsonObject.getJSONArray("tagid_list");
        List<Integer> ids = new ArrayList<Integer>();
        for(int i=0; i<tagidList.size(); i++){
            ids.add(tagidList.getInt(i));
        }
        return ids;
    }

    public String getOpensByTagId(int wxTagId, String appId, String nextOpenId) {
        if(nextOpenId == null){
            nextOpenId = "";
        }
        String url = Constants.BASEURL + "user/tag/get?access_token=" + coreBiz.getToken(appId);
        String param = "{   \"tagid\" : "+ wxTagId +",   \"next_openid\":" + nextOpenId +"}";
        String ret = HttpConnUtil.doPostJson(url, param);
        System.out.println(ret);
        WxCurlUtil.ret(ret);
        return ret;
    }
}
