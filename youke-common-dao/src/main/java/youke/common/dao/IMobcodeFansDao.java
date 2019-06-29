package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TMobcodeFans;

import java.util.List;

public interface IMobcodeFansDao {
    int deleteByPrimaryKey(String mobile);

    int insertSelective(TMobcodeFans record);

    TMobcodeFans selectByPrimaryKey(String mobile);

    int updateByPrimaryKeySelective(TMobcodeFans record);

    List<String> selectFilterMobiles(@Param("dykId") String dykId, @Param("filterDay") int filterDay);

    @Select("SELECT * from t_mobcode_fans where mobile=#{mobile} AND youkeId=#{youkeId}")
    TMobcodeFans selectByMobile(@Param("mobile") String mobile, @Param("youkeId") String youkeId);

    @Update("UPDATE t_mobcode_fans SET lastSendTime = NOW() where youkeId=#{dykId} AND mobile = #{mobile}")
    int updateByYoukeIdAndMobile(@Param("mobile") String mobile, @Param("dykId") String dykId);
}