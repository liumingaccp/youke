package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TMarketActive;
import youke.common.model.vo.param.market.MarketQueryVo;
import youke.common.model.vo.result.market.MarketActivityVo;

import java.util.List;

public interface IMarketActiveDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TMarketActive record);

    TMarketActive selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TMarketActive record);

    List<MarketActivityVo> queryList(MarketQueryVo params);

    @Select("select * from t_market_active where appId=#{appId} and type = #{type} and state = 1")
    TMarketActive selectUnderWaySbActive(@Param("appId") String appId, @Param("type") int type);

    @Select("select * from t_market_active where appId=#{appId} and type = #{type} and (state = 1 OR state = 0)")
    List<TMarketActive> selectAllActive(@Param("appId") String appId, @Param("type") int type);

    @Update("UPDATE t_market_active SET state=1 WHERE NOW() BETWEEN begTime AND endTime AND state=0")
    int updateStateIng();

    @Update("UPDATE t_market_active SET state=2 WHERE NOW() > endTime AND state<2")
    int updateStateEnd();

    @Select("SELECT IFNULL(COUNT(id),0) FROM t_market_active WHERE youkeId = #{dykId} AND state = #{state}")
    int selectActiveNumForWaitData(@Param("dykId") String dykId,@Param("state") int state);

    @Select("select *  from t_market_active where appId=#{appId} and type = #{type} and (state = 1 OR state = 0) and id!=#{activeId} ORDER BY begTime")
    List<TMarketActive> selectActiveWithOutThis(@Param("appId")String appId, @Param("type") int type,@Param("activeId") Long activeId);

    @Select("select count(id) from t_market_active where appId=#{appId} and type = #{type} and state = 1")
    Long queryCountForType(@Param("appId") String appId, @Param("type") Integer type);

    @Select("SELECT title from t_market_active where id =#{activeId}")
    String selectTitleByPrimaryKey(@Param("activeId") Long activeId);
}