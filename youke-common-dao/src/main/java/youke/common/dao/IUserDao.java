package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TPosition;
import youke.common.model.TUser;
import youke.common.model.vo.result.AdminAccountVo;
import youke.common.model.vo.result.CurUserVo;
import youke.common.model.vo.result.CurrentAccountVo;
import youke.common.model.vo.result.SubAccountListRetVo;

import java.util.List;

public interface IUserDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    @Select("SELECT * from t_user")
    List<TUser> selectAll();

    @Select("select id from t_user where mobile=#{mobile}")
    Integer selectIdByMobile(@Param("mobile") String mobile);

    @Update("UPDATE t_user SET password = #{password} WHERE mobile = #{mobile}")
    int updatePassByMobile(@Param("mobile") String mobile, @Param("password") String password);

    @Select("SELECT mobile FROM t_user WHERE mobile = #{mobile}")
    String selectMobile(String mobile);

    AdminAccountVo getAccount(String youkeId);

    CurUserVo getCurrentUser(@Param("userId")Integer userId);

    @Select("select id,mobile,youkeId,role,state from t_user where mobile=#{mobile} and password=#{password}")
    TUser selectUserByLogin(@Param("mobile") String mobile, @Param("password") String password);

    CurrentAccountVo getCurrentAccount(Integer userId);

    @Update("update t_user set followOpenId=#{openId},followSubscr=1 where mobile=#{mobile}")
    int updateUserOpenId(@Param("mobile") String mobile, @Param("openId") String openId);

    @Update("update t_user set followSubscr=#{followSubscr} where followOpenId=#{openId}")
    int updateFollowSubscr(@Param("openId") String openId, @Param("followSubscr") Integer followSubscr);

    @Select("SELECT IFNULL(COUNT(id),0) from t_user where youkeId=#{dykId} and role != 0")
    Integer selectSubAccountNum(@Param("dykId") String dykId);

    @Select("SELECT id, mobile, truename as empName, createTime as createTime, state as state, expTime as expTime from t_user where youkeId=#{dykId} and role!=0 order by createTime DESC")
    List<SubAccountListRetVo> selectSubAccountList(@Param("dykId") String dykId);

    @Select("SELECT * FROM t_user WHERE mobile = #{mobile}")
    TUser selectUserByMobile(@Param("mobile") String mobile);

    @Select("SELECT id from t_user where role > 0 AND state=0 AND expTime IS NOT NULL AND TO_DAYS(NOW()) - TO_DAYS(expTime) >= 1")
    List<Integer> selectExpSubAccount();

}