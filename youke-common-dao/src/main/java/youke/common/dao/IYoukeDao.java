package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TYouke;
import youke.common.model.vo.result.AccountDataVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IYoukeDao {
    int deleteByPrimaryKey(String id);

    int insertSelective(TYouke record);

    TYouke selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TYouke record);

    @Update("update t_youke set vip=#{packageid},openTime=#{openTime},endTime=#{endTime},state=1 where id=#{youkeid}")
    int updateOpenVip(@Param("packageid") Integer packageid, @Param("openTime") Date openTime, @Param("endTime") Date endTime, @Param("youkeid") String youkeid);

    @Select("select company from t_youke where id=#{youkeid}")
    String selectCompany(@Param("youkeid") String youkeid);

    AccountDataVo getAccontData(String youkeId);

    @Update("update t_youke set money=money+#{price} where id=#{youkeid}")
    int updateMoney(@Param("price") Integer price, @Param("youkeid") String youkeid);

    @Select("select money from t_youke where id=#{youkeid}")
    int selectMoney(@Param("youkeid") String youkeid);

    @Update("update t_youke set extShopNum=extShopNum+1 where id=#{youkeid}")
    int updateExtShopNum(@Param("youkeid") String youkeid);

    @Update("update t_youke set state=2 where endTime is not null and endTime<Now()")
    int updateExpireState();

    @Select("select a.mobile,a.followOpenId,b.company, b.endTime,b.vip,a.youkeId FROM t_user a INNER JOIN t_youke b ON a.youkeId=b.id WHERE a.role=0 and b.state=1 and b.vip>0 and b.endTime=#{date}")
    List<Map> selectExpireUser(@Param("date") String date);

    @Select("SELECT * from t_youke where state = 1")
    List<TYouke> selectAll();

    @Select("select id from t_youke where state between 1 and 2")
    List<String> selectIds();

    @Select("select a.company as title,b.followOpenId as openId,b.mobile FROM t_youke a INNER JOIN t_user b ON a.`id`=b.`youkeId` WHERE b.role=0 AND a.`id`=#{youkeId}")
    Map<String, String> selectYoukeOpenMap(@Param("youkeId") String youkeId);

    @Select("select id from t_youke where endTime=#{date}")
    List<String> selectExpireIds(@Param("date") String date);

    @Select("SELECT a.id FROM t_youke a INNER JOIN t_subscr b ON a.id=b.`youkeId` INNER JOIN t_subscr_config c ON b.`appId`=c.`appId` WHERE a.vip>0 AND a.state=1 AND c.`payState`=1 ORDER BY a.`vip` DESC")
    List<String> selectOKPayYoukeId();

    @Select("SELECT a.mobile,a.followOpenId,b.company,b.createTime, b.endTime,b.vip,a.youkeId FROM t_user a INNER JOIN t_youke b ON a.youkeId=b.id WHERE a.role=0 AND b.state=1 AND b.vip=0 AND b.`createTime`>#{date} ORDER BY b.`createTime` DESC")
    List<Map> selectFirstSuccUser(@Param("date") Date date);

    @Update("UPDATE t_youke SET subAccountNum = subAccountNum + #{num} where id = #{youkeId}")
    int updateSubAccountNum(@Param("youkeId") String youkeId, @Param("num") int num);
}