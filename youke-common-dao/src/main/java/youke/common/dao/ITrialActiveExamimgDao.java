package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TTrialActiveExamimg;

public interface ITrialActiveExamimgDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TTrialActiveExamimg record);

    TTrialActiveExamimg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTrialActiveExamimg record);

    @Select("select picUrl from t_trial_active_examimg where activeId = #{activeId} and openId = #{openId} and appId = #{appId}")
    String selectPic(@Param("activeId") long activeId, @Param("openId") String openId, @Param("appId") String appId);
}