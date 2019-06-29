package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TSubscrConfig;
import youke.common.model.vo.result.PayConfigVo;
import youke.common.model.vo.result.TagRuleStateVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ISubscrConfigDao {

    int insertSelective(TSubscrConfig record);

    TSubscrConfig selectByPrimaryKey(String appId);

    int updateByPrimaryKeySelective(TSubscrConfig record);

    void updateByAppid(@Param("type") int type, @Param("appId") String appId);

    @Select("select payBuss,payState,payNumber,paycert,validtimeBeg,validtimeEnd,filecert from t_subscr_config where appId=#{appId}")
    PayConfigVo selectPayConfig(@Param("appId")String appId);

    /**
     * 更新系统规则状态
     *
     * @param open
     */
    int updateState(@Param("open") boolean open,@Param("type") int type,@Param("appId") String appId);

    /**
     * 获取系统标签规则状态
     * @param appId
     * @return
     */
    TagRuleStateVo getRuleState(String appId);

    /**
     * 获取公众号标签开启配置
     * @return
     */
    @Select("select appId,openTagRule0,openTagRule1,openTagRule2 from t_subscr_config")
    List<Map> selectOpenTagRules();

    @Update("update t_subscr_config set openKefu = 1 where appId = #{appId} ")
    void updateKefuAppid(String appId);

    /**
     * 获取支付状态
     * @param appId
     * @return
     */
    @Select("select payState from t_subscr_config where appId=#{appId}")
    int selectPayState(@Param("appId") String appId);

    @Select("SELECT b.nickName as title,a.payNumber,a.validtimeEnd as expTime,c.followOpenId,c.mobile,b.youkeId FROM t_subscr_config a INNER JOIN t_subscr b ON a.`appId`=b.appId inner join t_user c on b.youkeId=c.youkeId WHERE c.role=0 AND a.validtimeEnd=#{date}")
    List<Map> selectExpireCert(@Param("date")String date);
}