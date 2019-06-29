package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TMarketActiveSubRedrule;

import java.util.List;

public interface IMarketActiveSubRedruleDao {
    int deleteByPrimaryKey(Long activeid);

    int insert(TMarketActiveSubRedrule record);

    int insertSelective(TMarketActiveSubRedrule record);

    TMarketActiveSubRedrule selectByPrimaryKey(Long activeid);

    int updateByPrimaryKeySelective(TMarketActiveSubRedrule record);

    int updateByPrimaryKey(TMarketActiveSubRedrule record);

    @Select("select openMidLimit, midLimitBeg, midLimitEnd, midMoneyBack, midRandMoneyBeg, midRandMoneyEnd from t_market_active_subredrule where activeId =#{activeId}")
    List<TMarketActiveSubRedrule> selectRulesByActiveId(@Param("activeId") Long id);

    @Select("select openMidLimit, midLimitBeg, midLimitEnd, midMoneyBack, midRandMoneyBeg, midRandMoneyEnd from t_market_active_subredrule where activeId =#{activeId} and openMidLimit = 1")
    List<TMarketActiveSubRedrule> selectOpenRulesByActiveId(@Param("activeId") Long id);
}