package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TMarketActiveRecord;
import youke.common.model.vo.param.market.MarketRecordListQueryVo;
import youke.common.model.vo.result.ActivePayVo;
import youke.common.model.vo.result.market.MarketExamineDetailVo;
import youke.common.model.vo.result.market.MarketRecordListVo;
import youke.common.model.vo.result.market.ActivityRecordDetailsVo;
import youke.common.model.vo.result.market.ActivityRecordVo;

import java.util.List;
import java.util.Map;

public interface IMarketActiveRecordDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TMarketActiveRecord record);

    TMarketActiveRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TMarketActiveRecord record);

    int updateByPrimaryKey(TMarketActiveRecord record);
    List<MarketRecordListVo> getRecordList(MarketRecordListQueryVo params);

    MarketExamineDetailVo getExamineDetail(@Param("recordId") int recordId, @Param("dykId") String dykId);

    @Select("select IFNULL(count(tmar.id),0) from t_market_active tma LEFT JOIN t_market_active_record tmar ON tma.id = tmar.activeId where tma.type=#{type} AND tmar.orderno = #{orderno} AND tmar.youkeId=#{youkeId}")
    int selectRecordByTypeAndOrderNo(@Param("type") Integer type, @Param("orderno") String orderno, @Param("youkeId") String youkeId);

    @Select("select IFNULL(count(id),0) from t_market_active_record where activeId=#{activeId} AND openId = #{openId}")
    int selectJoinNumberByActiveIdAndOpenId(@Param("activeId") Long activeId, @Param("openId") String openId);

    List<ActivityRecordVo> getParticipationList(String openId);

    ActivityRecordDetailsVo getRecordDetails(@Param("openId") String openId, @Param("recordId") long recordId);

    @Select("SELECT b.id,a.`type`,a.title,a.`youkeId`,b.`openId`,b.`rewardType`,b.`money`,b.`integral`,a.`appId` FROM t_market_active a INNER JOIN t_market_active_record b ON a.`id`=b.`activeId` WHERE a.examineType=1 AND a.`type` IN (0,1,2,8) AND a.examineHour>0 AND b.`state`=0 AND TIMESTAMPDIFF(HOUR,b.`createTime`,NOW())>=a.`examineHour`")
    List<Map> selectMonitorRecord();

    @Update("update t_market_active_record set state=#{state},backTime=NOW() where id=#{id}")
    int updateState(@Param("id") long id, @Param("state") int state);

    @Select("SELECT IFNULL(count(id),0) FROM t_market_active_record WHERE youkeId = #{dykId} AND state = 0")
    int selectWaitCheckNum(String dykId);

    @Select("SELECT SUM(money) FROM t_market_active_record WHERE rewardType<2 AND money IS NOT NULL AND state=4 AND youkeId=#{youkeId}")
    Integer selectTotalFailMoney(@Param("youkeId") String youkeId);

    @Select("SELECT a.id,a.openId,b.`type` as comeType,a.money,a.appId,a.youkeId FROM t_market_active_record a INNER JOIN t_market_active b ON a.`activeId`=b.`id` WHERE a.rewardType<2 AND a.money IS NOT NULL AND a.state=4 AND a.youkeId=#{youkeId}")
    List<ActivePayVo> selectActivePayVo(@Param("youkeId") String youkeId);

    List<ActivityRecordVo> getRecordDetailList(@Param("openId") String openId, @Param("type") int type);

    Map<String,Object> getConsumption(MarketRecordListQueryVo params);

    @Select("SELECT activeId from t_market_active_record where openId = #{openId} AND appId = #{appId} order by createTime DESC limit 1")
    Long selectLastActiveByOpenId(@Param("openId") String openId,@Param("appId") String appId);
}