package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TCollageActiveTuan;
import youke.common.model.vo.result.helper.CollageTuanItem;
import youke.common.model.vo.result.helper.TuanDetailVo;

import java.util.List;
import java.util.Map;

public interface ICollageActiveTuanDao {
    int deleteByPrimaryKey(Long id);

    int insert(TCollageActiveTuan record);

    int insertSelective(TCollageActiveTuan record);

    TCollageActiveTuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCollageActiveTuan record);

    int updateByPrimaryKey(TCollageActiveTuan record);

    TuanDetailVo getTuanDetailByOpenId(@Param("appId") String appId, @Param("openId") String openId, @Param("tuanId") Integer tuanId);

    List<CollageTuanItem> selectByActiveId(@Param("appId") String appId, @Param("activeId") Integer activeId);

    void updateStateForBeg(@Param("ids") List<Long> ids);

    @Select("SELECT tuan.id, tuan.appId, active.title FROM t_collage_active_tuan tuan Inner JOIN t_collage_active active ON tuan.activeId = active.id WHERE tuan.state = 0 AND tuan.endTime < NOW()")
    List<Map> selectEndTuan();
}