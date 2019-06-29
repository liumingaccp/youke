package youke.facade.wx.provider;

import com.github.pagehelper.PageInfo;
import youke.facade.wx.vo.reply.AutoReplyVo;
import youke.facade.wx.vo.reply.KeyReplyVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/5
 * Time: 19:18
 */
public interface IWeixinAutoReplyService {

    void saveReply(int type, String content,String mediaType,int materialId,String appId);

    /**
     * 添加关键词回复
     * @param title
     * @param keys
     * @param mediaType
     * @param content
     * @param materialId
     * @return id
     */
    Integer addKeyReply(String title, List<String> keys, String mediaType, String content, int materialId, Integer keyMacth,String appId);

    /**
     * 编辑关键词回复
     * @param id
     * @param title
     * @param keys
     * @param mediaType
     * @param content
     * @param materialId
     */
    void saveKeyReply(Integer id, String title,List<String> keys, String mediaType, String content, int materialId,Integer keyMatch,String appId);

    /**
     * 获取关键词回复列表
     * @param title
     * @param key
     * @param appId
     * @param page
     * @param limit
     * @return
     */
    PageInfo<KeyReplyVo> getKeyReplys(String title, String key, String appId, int page, int limit);

    /**
     * @param type
     *      0 : 关闭
     *      1 : 开启
     * @param appId
     */
    void do_on_ofReply(int type, String appId);

    /**
     * @param type
     *      0 : 关注回复
     *      1 : 默认回复
     * @param appId
     */
    void delReply(int type, String appId);

    /**
     * @param type
     *      0 : 关注回复
     *      1 : 默认回复
     * @param appId
     */
    AutoReplyVo getReply(int type, String appId);

    KeyReplyVo getKeyReply(int ruleId);

    /**
     * @param id
     *      0 : 关注回复
     *      1 : 默认回复
     * @param appId
     */
    void delKeyReply(int id, String appId);
}
