package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TMobcode;

import java.util.List;
import java.util.Map;

public interface IMobcodeDao {
    int deleteByPrimaryKey(String youkeid);

    int insertSelective(TMobcode record);

    TMobcode selectByPrimaryKey(String youkeid);

    int updateByPrimaryKeySelective(TMobcode record);

    /**
     * 增加或减少短信账户的剩余数量
     *
     * @param totalcount
     * @param youkeId
     * @return
     */
    @Update("update t_mobcode set `count`=`count`+#{totalcount} where youkeId=#{youkeId}")
    int updateAddCount(@Param("totalcount") Integer totalcount, @Param("youkeId") String youkeId);

    /**
     * 减少冻结数量
     *
     * @param dykId
     */
    @Update("update t_mobcode SET icecount = icecount - 1 where youkeId = #{dykId}")
    int updateIceCount(@Param("dykId") String dykId);

    /**
     * 减少冻结数量,恢复短信数量
     *
     * @param dykId
     */
    @Update("update t_mobcode SET icecount = icecount - #{count}, count = count + #{count} where youkeId = #{dykId}")
    int updateCountAndIceCount(@Param("dykId") String dykId, @Param("count") int count);

    @Select("SELECT a.youkeId,c.mobile,c.`followOpenId`,a.`count` FROM t_mobcode a INNER JOIN t_youke b ON a.`youkeId`=b.`id` INNER JOIN t_user c ON a.`youkeId`=c.`youkeId` AND c.`role`=0 WHERE  a.`count` >0 and a.`count` < #{limitNum} ")
    List<Map> selectMonitorCount(@Param("limitNum") int limitNum);
}