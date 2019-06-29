package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TWxPerAutoLikeRecord;
import youke.common.model.vo.param.wxper.AutoLikeRecordQueryVo;
import youke.common.model.vo.result.wxper.AutoLikeRecordQueryRetVo;

import java.util.List;

public interface IWxPerAutoLikeRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerAutoLikeRecord record);

    int insertSelective(TWxPerAutoLikeRecord record);

    TWxPerAutoLikeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerAutoLikeRecord record);

    int updateByPrimaryKey(TWxPerAutoLikeRecord record);

    List<AutoLikeRecordQueryRetVo> selectLikeRecordList(AutoLikeRecordQueryVo params);

    @Select("SELECT * from t_wxper_autolike_record where taskId=#{taskId} AND youkeId=#{youkeId} AND to_days(likeDate) = to_days(now())")
    TWxPerAutoLikeRecord selectRecordByTaskId(@Param("taskId") Long taskId,@Param("youkeId") String youkeId);
}