package youke.service.wx.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.facade.wx.provider.IWeixinAutoReplyService;
import youke.facade.wx.vo.reply.AutoReplyVo;
import youke.facade.wx.vo.reply.KeyReplyVo;
import youke.service.wx.biz.IReplyBiz;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/5
 * Time: 19:21
 */
@Service
public class WeixinAutoReplyService implements IWeixinAutoReplyService {

    @Resource
    private IReplyBiz replyBiz;

    public void saveReply(int type, String content, String mediaType, int materialId, String appId) {
        //关注回复
        if(type == 0){
            replyBiz.saveSubscribeReply(content,mediaType,materialId,appId);
        }
        //自动回复
        if(type == 1){
            replyBiz.saveAutoReply(content,mediaType,materialId,appId);
        }
    }

    public Integer addKeyReply(String title, List<String> keys, String mediaType, String content, int materialId, Integer keyMacth, String appId) {
        return replyBiz.addKeyReply(title,keys,mediaType,content,materialId,keyMacth,appId);
    }

    public void saveKeyReply(Integer id, String title, List<String> keys, String mediaType, String content, int materialId,Integer keyMatch, String appId) {
        replyBiz.saveKeyReply(id, title,keys, mediaType, content, materialId,keyMatch, appId);
    }

    public PageInfo<KeyReplyVo> getKeyReplys(String title, String key, String appId, int page, int limit) {
        return replyBiz.getKeyReplys(title, key, appId, page, limit);
    }

    public void do_on_ofReply(int type, String appId) {
        replyBiz.do_on_ofReply(type, appId);
    }

    public void delReply(int type, String appId) {
        if(type == 0){
            replyBiz.delSubscribeReply(appId);
        }

        if(type == 1){
            replyBiz.delAutoReply(appId);
        }
    }

    public AutoReplyVo getReply(int type, String appId) {
        //关注回复
        if(type == 0){
            return replyBiz.getSubscribeReply(appId);
        }
        //自动回复
        if(type == 1){
            return replyBiz.getAutoReply(appId);
        }
        return null;
    }


    @Override
    public KeyReplyVo getKeyReply(int ruleId) {

        return replyBiz.getKeyReply(ruleId);
    }

    @Override
    public void delKeyReply(int id, String appId) {
        replyBiz.delKeyReply(id, appId);
    }
}
