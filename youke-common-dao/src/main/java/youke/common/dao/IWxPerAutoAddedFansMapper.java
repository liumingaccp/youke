package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TWxPerAutoAddedFans;

import java.util.Date;

public interface IWxPerAutoAddedFansMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerAutoAddedFans record);

    int insertSelective(TWxPerAutoAddedFans record);

    TWxPerAutoAddedFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerAutoAddedFans record);

    int updateByPrimaryKey(TWxPerAutoAddedFans record);

    /**
     * 查询通过人数
     *
     * @param dateBefore1 大于这个时间
     * @param dataBefore2 小于这个时间
     * @return
     */
    Integer selectByWxAccountIdAndDeviceId(@Param("youkeId") String youkeId, @Param("dateBefore1") Date dateBefore1, @Param("dateBefore2") Date dataBefore2, @Param("id") long id);

    @Select("SELECT IFNULL(sum(addNum),0) FROM t_wxper_autoadded_record WHERE youkeId = #{youkeId} and to_days(addTime) = to_days(now()) and taskId = #{taskId}")
    Integer selectTodayPassNum(@Param("youkeId") String youkeId, @Param("taskId") Long id);

    @Select("SELECT IFNULL(sum(addNum),0) FROM t_wxper_autoadded_record WHERE youkeId = #{youkeId} and TO_DAYS(NOW()) - TO_DAYS(addTime) <= 1 and taskId = #{taskId}")
    Integer selectYesterdayPassNum(@Param("youkeId") String youkeId, @Param("taskId") Long id);
}