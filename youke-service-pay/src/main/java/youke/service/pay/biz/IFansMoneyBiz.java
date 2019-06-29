package youke.service.pay.biz;

import youke.common.model.vo.result.MoneyRecordVo;

import java.util.List;

public interface IFansMoneyBiz {

    /**
     * 获取粉丝零钱总数
     * @param openId
     * @return
     */
    int getMoneyTotal(String openId);

    /**
     * 获取零钱明细
     * @param openId
     * @return
     */
    List<MoneyRecordVo> getMoneyList(String openId);


    /**
     * 添加零钱
     * @param comeType
     * @param title
     * @param openId
     * @param money
     * @param appId
     * @param youkeId
     */
    void addMoney(Integer comeType,String title, String openId, int money, String appId, String youkeId);

}
