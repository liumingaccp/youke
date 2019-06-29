package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TSubscrFansMoney;

import java.util.List;

public interface ISubscrFansMoneyDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TSubscrFansMoney record);

    TSubscrFansMoney selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSubscrFansMoney record);

    @Select("select sum(money) from t_subscr_fans_money where openId=#{openId}")
    Integer selectSumMoney(@Param("openId") String openId);

    @Select("select * from t_subscr_fans_money where openId=#{openId} order by createTime desc")
    List<TSubscrFansMoney> selectMoneyList(@Param("openId") String openId);
}