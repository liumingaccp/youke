package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import youke.common.model.TTaowxPoster;

import java.util.List;

public interface ITaowxPosterDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TTaowxPoster record);

    TTaowxPoster selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTaowxPoster record);

    List<TTaowxPoster> queryPage(@Param("timeStart") String timeStart, @Param("timeEnd")String timeEnd, @Param("dykId")String dykId);
}