package youke.service.wx.biz;

import youke.common.model.vo.file.SerializeMultipartFile;
import youke.facade.wx.vo.kefu.KefuStateVo;
import youke.facade.wx.vo.kefu.KefuVo;

import java.util.List;

/**
 * 客服业务处理
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738726
 */
public interface IKefuBiz {

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

     void saveHeadImage(String account, SerializeMultipartFile file, String appId,int id);

    /**
     * 删除客服
     * @param account
     */
     void deleteKefu(int id, String account, String appId);

    /**
     * 绑定客服微信
     * @param account
     * @param wechat
     */
     void saveBindKefuWechat(int id, String account,String wechat, String appId);

    /**
     * 获取在线客服列表 redis缓存5分钟
     * @param appId
     * @return
     */
     List<KefuStateVo> getKefuStateList(String appId);

     void doSyncKefu(String appId);
}
