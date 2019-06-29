package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TMarketActiveSign;

public interface IMarketActiveSignDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TMarketActiveSign record);

    TMarketActiveSign selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TMarketActiveSign record);

    @Select("select id, activeId, openId, runDay, lastRunDay, lastDate, youkeId from t_market_active_sign where activeId = #{activeId} AND openId=#{openId}")
    TMarketActiveSign selectSignRecord(@Param("openId") String openId, @Param("activeId") Long activeId);
}