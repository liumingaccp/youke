package youke.facade.wx.provider;

import youke.common.model.vo.file.SerializeMultipartFile;
import youke.facade.wx.vo.kefu.KefuStateVo;
import youke.facade.wx.vo.kefu.KefuVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/6
 * Time: 9:05
 */
public interface IWeixinKefuService {
    /**
     * 获取客服列表
     * @param appId
     * @return
     */
    List<KefuVo> getKefus(String appId);

    /**
     * 添加客服,并传入图像
     * @param file
     * @param nickName
     * @param account
     */
    void addKefu(SerializeMultipartFile file, String nickName, String account, String appId);

    void postHeadImage(String account, SerializeMultipartFile file, String appId, int id);

    /**
     * 删除客服
     * @param account
     */
    void deleteKefu(String account, String appId,int id);

    /**
     * 绑定客服微信
     * @param account
     * @param wechat
     */
    void saveBindKefuWechat(String account,String wechat, String appId,int id);

    /**
     * 获取在线客服列表 redis缓存5分钟
     * @param appId
     * @return
     */
    List<KefuStateVo> getKefuStateList(String appId);
}
