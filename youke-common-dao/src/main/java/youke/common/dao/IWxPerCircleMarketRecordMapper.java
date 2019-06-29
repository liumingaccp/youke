package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TWxPerCircleMarketRecord;
import youke.common.model.vo.param.wxper.CircleMarketRecordQueryVo;
import youke.common.model.vo.result.wxper.CircleMarketRecordQueryRetVo;

import java.util.Date;
import java.util.List;

public interface IWxPerCircleMarketRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerCircleMarketRecord record);

    int insertSelective(TWxPerCircleMarketRecord record);

    TWxPerCircleMarketRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerCircleMarketRecord record);

    int updateByPrimaryKey(TWxPerCircleMarketRecord record);

    List<CircleMarketRecordQueryRetVo> selectRecordList(CircleMarketRecordQueryVo params);

    @Select("SELECT * from t_wxper_circlemarket_record where taskId=#{taskId} AND deviceId=#{deviceId}")
    TWxPerCircleMarketRecord selectRecordByTaskId(@Param("taskId") Long taskId, @Param("deviceId") Long deviceId);

    @Select("SELECT id from t_wxper_circlemarket_record where taskId=#{taskId} AND deviceId=#{deviceId}")
    Long selectRecordId(@Param("taskId") long taskId, @Param("deviceId") Long deviceId);

    @Select("SELECT sendTime from t_wxper_circlemarket_record where taskId=#{taskId} ORDER BY id DESC limit 1")
    Date selectLastTaskTime(Long taskId);

    @Update("update t_wxper_circlemarket_record set state = 3 where state = 1 and abs(TIMESTAMPDIFF(MINUTE,sendTime,NOW())) > 5")
    void updateUnReturn();
}