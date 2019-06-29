package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TTaokeActive;
import youke.common.model.vo.param.helper.TaokeQueryVo;
import youke.common.model.vo.result.TaokeActiveVo;
import youke.common.model.vo.result.helper.TaokeCountDataVo;
import youke.common.model.vo.result.helper.TaokeQueryRetVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ITaokeActiveDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TTaokeActive record);

    TTaokeActive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTaokeActive record);

    @Delete("delete from t_taoke_active where id = #{activeId} and state = 0")
    void deleteByKey(Integer activeId);

    List<TaokeQueryRetVo> queryList(TaokeQueryVo params);

    @Select("SELECT state FROM t_taoke_active where id = #{activeId}")
    Integer selectState(Integer activeId);

    @Update("update t_taoke_active set state = 3 where id = #{activeId}")
    void updateState(Integer activeId);

    TaokeCountDataVo selectCountData(Integer id);

    @Select("select COUNT(torder.id) from t_taoke_active_order torder left join t_taoke_active active on active.id = torder.activeId where torder.activeId = #{id}")
    Integer queryCount(Integer id);

    List<Map<String,Date>> getActiveTimeList(@Param("startTime") Date starttime, @Param("endTime")Date endTime);

    @Update("UPDATE t_taoke_active SET state=1 WHERE NOW() BETWEEN startTime AND endTime AND state=0")
    int updateStateIng();

    @Update("UPDATE t_taoke_active SET state=2 WHERE NOW() > endTime AND state<2")
    int updateStateEnd();

    TaokeActiveVo selectDetail(@Param("id") Integer activeId, @Param("youkeId") String youkeId);

    @Select("select count(id) from t_taoke_active where youkeId = #{appId}")
    Long selectActiveCount(String appId);
}