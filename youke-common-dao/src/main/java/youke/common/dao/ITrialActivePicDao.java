package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TTrialActivePic;
import youke.common.model.vo.param.helper.H5QueryPicVo;

import java.util.List;

public interface ITrialActivePicDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TTrialActivePic record);

    TTrialActivePic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTrialActivePic record);

    List<TTrialActivePic> selectPics(H5QueryPicVo params);

    Integer selectPicsCount(@Param("activeId") Integer id, @Param("dyk") String youkeId);

    @Delete("delete from t_trial_active_pic where activeId = #{activeId} and youkeId = #{youkeId}")
    void deleteByActive(@Param("activeId") Integer activeId, @Param("youkeId") String youkeId);
}