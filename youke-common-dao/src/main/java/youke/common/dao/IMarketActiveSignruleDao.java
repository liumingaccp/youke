package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TMarketActiveSignrule;

import java.util.List;

public interface IMarketActiveSignruleDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TMarketActiveSignrule record);

    TMarketActiveSignrule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TMarketActiveSignrule record);

    @Delete("delete from t_market_active_signrule where activeId = #{activeId} AND youkeId = #{dykId}")
    void deleteRuleByActiveId(@Param("activeId") Long activeId, @Param("dykId") String dykId);

    @Select("select id, activeId, runDay, integral, youkeId from t_market_active_signrule where activeId = #{activeId} AND  youkeId=#{youkeId} ORDER BY runDay ASC")
    List<TMarketActiveSignrule> selectSignRule(@Param("activeId") Long activeId,@Param("youkeId") String youkeId);
}