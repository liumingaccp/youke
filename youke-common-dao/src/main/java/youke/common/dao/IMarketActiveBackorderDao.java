package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TMarketActiveBackorder;

import java.util.List;

public interface IMarketActiveBackorderDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TMarketActiveBackorder record);

    TMarketActiveBackorder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TMarketActiveBackorder record);

    @Select("select id, orderno, money, remark, createTime, activeId, state, `type`, youkeId from t_market_active_backorder where activeId = #{activeId} AND youkeId = #{dykId} AND type = #{type} AND state = 0")
    List<TMarketActiveBackorder> selectBackordersByActiveId(@Param("activeId") Long activeId, @Param("dykId") String dykId, @Param("type") Integer type);

    @Select("select id, orderno, money, remark, createTime, activeId, state, `type`, youkeId from t_market_active_backorder where orderno = #{orderno} AND activeId = #{activeId}")
    TMarketActiveBackorder selectBackorderByOrderNo(@Param("orderno") String orderno, @Param("activeId") Long activeId);

    @Delete("delete from t_market_active_backorder where activeId = #{activeId}")
    void deleteBackOrder(Long activeId);

    @Select("select id, orderno, money, remark, createTime, activeId, state, `type`, youkeId from t_market_active_backorder where activeId = #{activeId} AND type = #{type}")
    List<TMarketActiveBackorder> selectInputOrders(@Param("activeId") long activeId,@Param("type") int type);
}