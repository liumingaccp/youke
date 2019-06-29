package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TMarketActiveVote;
import youke.common.model.vo.result.ResultOfVotingVo;

import java.util.List;

public interface IMarketActiveVoteDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TMarketActiveVote record);

    TMarketActiveVote selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TMarketActiveVote record);

    List<ResultOfVotingVo> gettpdatas(@Param("activeId") Long activeId, @Param("dykId") String dykId);

    @Select("select COUNT(id) from t_market_active_vote where activeId = #{activeId} AND openId = #{openId} AND youkeId = #{youkeId}")
    int selectJoinNum(@Param("activeId") Long activeId, @Param("openId") String openId, @Param("youkeId") String youkeId);

    @Select("SELECT COUNT(DISTINCT openId) FROM t_market_active_vote av WHERE  activeId=#{activeId} AND youkeId = #{youkeId}")
    int getMaxJoinNum(@Param("activeId") Long activeId, @Param("youkeId") String youkeId);
}