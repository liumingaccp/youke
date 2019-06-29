package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TSubscrFans;
import youke.common.model.TTag;
import youke.common.model.vo.param.WxFansQueryVo;

import java.util.Date;
import java.util.List;

public interface ISubscrFansDao {
    int deleteByPrimaryKey(String openid);

    int insertSelective(TSubscrFans record);

    TSubscrFans selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(TSubscrFans record);

    List<String> queryIdList(WxFansQueryVo qo);

    List<TSubscrFans> queryList(WxFansQueryVo qo);

    List<TTag> selectTTagByOpenId(String openId);

    void saveRemark(@Param("openId") String openId, @Param("remark") String remark);

    List<TSubscrFans> queryBlackList(@Param("nickName") String nickName, @Param("appId") String appId);

    String isOpenId(String openid);

    void batchUpdateState(@Param("openIds") List<String> openIds, @Param("appid") String appid, @Param("state") int state);

    List<String> selectOpenIdsByTagId(Integer tagId);

    @Update("update t_subscr_fans set mobile=#{mobile} where openId=#{openId}")
    int updateMobile(@Param("openId") String openId, @Param("mobile") String mobile);

    @Update("update t_subscr_fans set lastTime=#{date} where openId=#{openId}")
    void updateLastTime(@Param("openId") String openId, @Param("date") Date date);

    /**
     * 获取所有关注公众号的手机号
     *
     * @param appId
     * @return
     */
    List<String> selectMobilesByAppId(String appId);

    int updateIntegralByOpenId(@Param("openId") String openId, @Param("appId") String appId, @Param("integral") Integer integral);

    @Select("SELECT integral FROM t_subscr_fans where openId = #{openId}")
    Integer getIntegral(String openId);

    @Select("select nickname from t_subscr_fans where openId = #{openId}")
    String selectNickname(@Param("openId") String openId);

    /**
     * 获取昨日新增粉丝数量
     *
     * @return
     */
    int getAddFansNumForYesterday(String appId);

    /**
     * 获取前一天新增粉丝数量
     *
     * @return
     */
    int getAddFansNumForTheDayBeforeYesterday(String appId);

    /**
     * 获取昨日活跃粉丝数量
     *
     * @return
     */
    int getActiveFansNumForYesterday(String appId);

    /**
     * 获取前一天活跃粉丝数量
     *
     * @return
     */
    int getActiveFansNumForTheDayBeforeYesterday(String appId);


    /**
     * 获取前七天新增粉丝数量
     *
     * @return
     */
    int getAddFansNumForSevenDays(String appId);

    /**
     * 获取前十四天新增粉丝数量
     *
     * @return
     */
    int getAddFansNumForElevenDays(String appId);

    /**
     * 根据appId查询是否存在粉丝
     *
     * @param appId
     * @return
     */
    int queryForCount(String appId);

    /**
     * 查询手机号是否被使用
     *
     * @param mobile
     * @param appId
     * @return
     */
    int selectByMobile(@Param("mobile") String mobile, @Param("appId") String appId);

    @Select("SELECT openId, state, integral, subState from t_subscr_fans where mobile = #{mobile} AND appId = #{appId} limit 1")
    TSubscrFans selectSubFansByMobile(@Param("mobile") String mobile,@Param("appId") String appId);

    @Select("SELECT openId, state, integral, subState from t_subscr_fans where nickname = #{nickname} AND appId = #{appId} limit 1")
    TSubscrFans selectSubFansByNickName(@Param("nickname") String nickname,@Param("appId") String appId);
}