package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TIntegralActive;
import youke.common.model.vo.result.IntegralActiveDetailVo;
import youke.common.model.vo.result.IntegralActiveRetVo;
import youke.common.model.vo.result.IntegralActiveVo;

import java.util.List;

public interface IIntegralActiveDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TIntegralActive record);

    TIntegralActive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TIntegralActive record);

    List<IntegralActiveVo> queryList(@Param("title") String title, @Param("state") int state, @Param("youkeId") String youkeId);

    IntegralActiveDetailVo getActiceById(@Param("activeId") int activeId, @Param("youkeId") String youkeId,  @Param("appId") String appId);

    @Update("update t_integral_active set state = 3 where id = #{activeId}")
    void updateState(int activeId);

    @Select("SELECT state FROM t_integral_active where id = #{activeId}")
    Integer selectState(int activeId);

    @Update("UPDATE t_integral_active SET state=1 WHERE NOW() BETWEEN startTime AND endTime AND state=0")
    int updateStateIng();

    @Update("UPDATE t_integral_active SET state=2 WHERE NOW() > endTime AND state<2")
    int updateStateEnd();

    IntegralActiveRetVo getactiveinfo(@Param("id") int activeId, @Param("youkeId") String youkeId);

    @Select("SELECT * FROM t_integral_active where id = #{id} and youkeId = #{youkeId}")
    TIntegralActive selectByPrimaryKeyAndYoukeId(@Param("id") Integer activeId, @Param("youkeId") String dykId);

}