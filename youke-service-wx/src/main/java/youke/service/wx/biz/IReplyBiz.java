package youke.service.wx.biz;

import com.github.pagehelper.PageInfo;
import youke.facade.wx.vo.reply.AutoReplyVo;
import youke.facade.wx.vo.reply.KeyReplyVo;

import java.util.List;

/**
 * 回复业务
 * @author liuming 2017-12-25
 */
public interface IReplyBiz {

    /**
     * 保存关注回复
     * @param content
     * @param mediaType
     * @param materialId
     */
    void saveSubscribeReply(String content,String mediaType,int materialId,String appId);

    void delSubscribeReply(String appId);

    AutoReplyVo getSubscribeReply(String appId);

    /**
     * 保存自动回复
     * @param content
     * @param mediaType
     * @param materialId
     */
    void saveAutoReply(String content,String mediaType,int materialId,String appId);

    void delAutoReply(String appId);

    AutoReplyVo getAutoReply(String appId);

    /**
     * 添加关键词回复
     * @param title
     * @param keys
     * @param mediaType
     * @param content
     * @param materialId
     * @param keyMatch
     * @param appId
     * @return id
     */
    Integer addKeyReply(String title,List<String> keys, String mediaType, String content, int materialId, Integer keyMatch, String appId);

    /**
     * 编辑关键词回复
     * @param id
     * @param title
     * @param keys
     * @param mediaType
     * @param content
     * @param materialId
     * @param keyMatch
     * @param appId
     */
    void saveKeyReply(Integer id, String title,List<String> keys, String mediaType, String content, int materialId,Integer keyMatch, String appId);

    void delKeyReply(Integer id, String appId);

    /**
     * 获取关键词回复列表
     * @param title
     * @param key
     * @param appId
     * @return
     */
    PageInfo<KeyReplyVo> getKeyReplys(String title, String key, String appId, int page, int limit);


    /**
     *  0 : 关闭
     *  1 : 开启
     * @param type
     */
    void do_on_ofReply(int type, String appId);

    /**
     * 推广关注活动业务
     * @param id
     * @param wxId
     * @param openId
     * @return
     */
    String doFollowActive(long id,String wxId,String openId);

    KeyReplyVo getKeyReply(int ruleId);

    String doCutpriceActive(long id, String wxId, String openId,String appId);

    String doCollageActive(String key, String wxId, String openId,String appId);

    String doMarketActive(int type, String wxId, String openId, String appId);

    String doIntegralActive(int id, String wxId, String openId, String appId);

    String doTrialActive(int id, String wxId, String openId, String appId);

    String doRebateActive(int id, String wxId, String openId, String appId);

    String doTaokeActive(int id, String wxId, String openId, String appId);
}
