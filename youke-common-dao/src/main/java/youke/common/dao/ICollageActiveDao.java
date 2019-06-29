package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TCollageActive;
import youke.common.model.vo.param.helper.CollageQueryVo;
import youke.common.model.vo.result.helper.CollageActiveDetailAndTuanVo;
import youke.common.model.vo.result.helper.CollageQueryRetVo;
import youke.common.model.vo.result.helper.CollageQueryRetVoByOpenId;
import youke.common.model.vo.result.helper.CollageQueryRetVoH5;

import java.util.List;

public interface ICollageActiveDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TCollageActive record);

    int insertSelective(TCollageActive record);

    TCollageActive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCollageActive record);

    int updateByPrimaryKey(TCollageActive record);

    List<CollageQueryRetVo> queryList(CollageQueryVo params);

    @Select("select * from t_collage_active where id = #{id} and appId = #{appId} and youkeId = #{youkeId}")
    TCollageActive selectByPrimaryKeyAndDyk(@Param("id") Integer activeId, @Param("appId") String appId, @Param("youkeId") String dykId);

    List<CollageQueryRetVoH5> queryListForH5(CollageQueryVo params);

    List<CollageQueryRetVoByOpenId> queryListForOpenId(CollageQueryVo params);

    CollageActiveDetailAndTuanVo selectActiveDetail(@Param("appId") String appId, @Param("activeId") Integer activeId);

    @Update("UPDATE t_collage_active SET state=1 WHERE NOW() BETWEEN startTime AND endTime AND state=0")
    int updateStateIng();

    @Update("UPDATE t_collage_active SET state=2 WHERE NOW() > endTime AND state<2")
    int updateStateEnd();

    @Select("select count(id) from t_collage_active where appId = #{appId}")
    Long selectActiveCount(@Param("appId")String appId);
}