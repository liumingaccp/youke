package youke.service.wx.biz.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.ISubscrFansDao;
import youke.common.dao.IUserDao;
import youke.common.model.TSubscrFans;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.StringUtil;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.util.Constants;
import youke.facade.wx.vo.fans.FansVo;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.biz.IFansBiz;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2017/12/28
 * Time: 18:09
 */
@Service
public class FansBiz extends Base implements IFansBiz {

    @Resource
    private ICoreBiz coreBiz;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IUserDao userDao;

    public void saveFans(FansVo fans) {
        TSubscrFans sFans = new TSubscrFans();
        sFans.setOpenid(fans.getOpenid());
        sFans.setNickname(fans.getNickname());
        sFans.setTruename(fans.getTruename());
        sFans.setMobile(fans.getMobile());
        sFans.setEmail(fans.getEmail());
        sFans.setSex(fans.getSex().intValue());
        sFans.setHeadimgurl(fans.getHeadimgurl());
        sFans.setCity(fans.getCity());
        sFans.setProvince(fans.getProvince());
        sFans.setCountry(fans.getCountry());
        sFans.setLanguage(fans.getLanguage());
        sFans.setUnionid(fans.getUnionid());
        sFans.setAppid(fans.getAppid());
        sFans.setGroupid(fans.getGroupId());
        sFans.setSubstate(0);
        sFans.setSubtime(new Date());
        sFans.setLasttime(new Date());
        if (subscrFansDao.isOpenId(fans.getOpenid()) != null) {
            subscrFansDao.updateByPrimaryKeySelective(sFans);
        } else {
            subscrFansDao.insertSelective(sFans);
        }
    }

    public boolean saveFans(String openId, String appId) {
        String ret = HttpConnUtil.doHttpRequest(Constants.BASEURL + "user/info?access_token=" + coreBiz.getToken(appId) + "&openid=" + openId + "&lang=zh_CN");
        WxCurlUtil.ret(ret);
        JSONObject obj = JSONObject.fromObject(ret);
        TSubscrFans sFans = new TSubscrFans();
        sFans.setOpenid(obj.getString("openid"));
        sFans.setNickname(obj.getString("nickname"));
        sFans.setSex(obj.getInt("sex"));
        String headImgUrl = obj.getString("headimgurl");
        if(notEmpty(headImgUrl))
            headImgUrl = headImgUrl.replace("http://","https://");
        sFans.setHeadimgurl(headImgUrl);
        sFans.setCity(obj.getString("city"));
        sFans.setProvince(obj.getString("province"));
        sFans.setCountry(obj.getString("country"));
        sFans.setLanguage(obj.getString("language"));
        sFans.setSubtime(new Date());
        sFans.setSubstate(0);
        sFans.setState(0);
        if (obj.containsKey("unionid"))
            sFans.setUnionid(obj.getString("unionid"));
        sFans.setAppid(appId);
        if (obj.containsKey("groupid"))
            sFans.setGroupid(obj.getInt("groupid"));
        if (subscrFansDao.isOpenId(sFans.getOpenid()) != null) {
            try {
                subscrFansDao.updateByPrimaryKeySelective(sFans);
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        } else {
            sFans.setLasttime(new Date());
            try {
                subscrFansDao.insertSelective(sFans);
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }

    }

    public void updateFansInBlack(String openId, String appId) {
        String json = "{\"openid_list\":[\"" + openId + "\"]}";
        String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "tags/members/batchblacklist?access_token=" + coreBiz.getToken(appId), json);
        WxCurlUtil.ret(ret);
    }

    public void updateFansOutBlack(String openId, String appId) {
        String json = "{\"openid_list\":[\"" + openId + "\"]}";
        String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "tags/members/batchunblacklist?access_token=" + coreBiz.getToken(appId), json);
        WxCurlUtil.ret(ret);
    }

    public void updateFansInBlack(List<String> openIds, String appId) {
        int time = openIds.size() % 20 == 0 ? openIds.size() / 20 : openIds.size() / 20 + 1;
        if (time > 1) {
            for (int i = 0; i < time - 1; i++) {
                List<String> sub = openIds.subList(i * 20, (i + 1) * 20);
                String json = getJsonStr(sub);
                String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "tags/members/batchblacklist?access_token=" + coreBiz.getToken(appId), json);
                WxCurlUtil.ret(ret);
            }
        }
        //最后一次
        List<String> sub = openIds.subList((time - 1) * 20, openIds.size());
        String json = getJsonStr(sub);
        String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "tags/members/batchblacklist?access_token=" + coreBiz.getToken(appId), json);
        WxCurlUtil.ret(ret);
    }

    public void updateFansOutBlack(List<String> openIds, String appId) {
        int time = openIds.size() % 20 == 0 ? openIds.size() / 20 : openIds.size() / 20 + 1;
        if (time > 1) {
            for (int i = 0; i < time - 1; i++) {
                List<String> sub = openIds.subList(i * 20, (i + 1) * 20);
                String json = getJsonStr(sub);
                String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "tags/members/batchunblacklist?access_token=" + coreBiz.getToken(appId), json);
                WxCurlUtil.ret(ret);
            }
        }
        //最后一次
        List<String> sub = openIds.subList((time - 1) * 20, openIds.size());
        String json = getJsonStr(sub);
        String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "tags/members/batchunblacklist?access_token=" + coreBiz.getToken(appId), json);
        WxCurlUtil.ret(ret);
    }

    public void updateFansLastTime(String openId) {
        subscrFansDao.updateLastTime(openId, new Date());
    }

    @Override
    public void doSyncFans(String appId) {
        eachFans(appId,null);
    }

    private void eachFans(String appId,String nextOpenId){
        String url = Constants.BASEURL + "user/get?access_token=" + coreBiz.getToken(appId);
        if(notEmpty(nextOpenId))
            url+="&next_openid="+nextOpenId;
        String ret = HttpConnUtil.doHttpRequest(url);
        System.out.println(url);
        System.out.println(ret);
        JSONObject resJson = JSONObject.fromObject(ret);
        if(!resJson.has("errcode")) {
            if (resJson.getInt("count") > 0) {
                JSONObject dataJson = resJson.getJSONObject("data");
                JSONArray openIdArr = dataJson.getJSONArray("openid");
                for (int i = 0; i < openIdArr.size(); i++) {
                    String openId = openIdArr.getString(i);
                    saveFans(openId, appId);
                }
                if (dataJson.has("next_openid") && notEmpty(dataJson.getString("next_openid"))) {
                    eachFans(appId, dataJson.getString("next_openid"));
                }
            }
        }
    }

    public String getJsonStr(List<String> openIds) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"openid_list\":[");
        for (String str : openIds) {
            sb.append("\"" + str + "\",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]}");

        return sb.toString();
    }


}
