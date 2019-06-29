package youke.facade.pay.provider;

import youke.common.model.vo.result.IntegralRecordVo;

import java.util.List;

/**
 * 微信粉丝积分
 */
public interface IIntegralService {

    /**
     * 获取粉丝积分总数
     * @param openId
     * @return
     */
    int getIntegralTotal(String openId);

    /**
     * 获取积分明细
     * @param openId
     * @return
     */
    List<IntegralRecordVo> getIntegralList(String openId);


    /**
     * 添加积分
     * @param comeType
     * @param title
     * @param openId
     * @param integral
     * @param appId
     * @param youkeId
     */
    void addIntegral(int comeType,String title,String openId,int integral,String appId,String youkeId);

}
