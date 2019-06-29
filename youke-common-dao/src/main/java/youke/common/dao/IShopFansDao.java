package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TShopFans;
import youke.common.model.TTag;
import youke.common.model.vo.param.MassSMSParamVo;
import youke.common.model.vo.param.ShopFansQueryVo;
import youke.common.model.vo.result.MassTempListVo;

import java.util.List;

public interface IShopFansDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TShopFans record);

    TShopFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TShopFans record);

    List<TShopFans> queryForList(ShopFansQueryVo qo);

    List<Integer> getFansIds(ShopFansQueryVo qo);

    List<TTag> selectTTagById(int id);

    void sveRemark(@Param("fansId") long fansId, @Param("remark") String remark);

    List<MassTempListVo> getShopSendList(MassSMSParamVo params);

    TShopFans getByMobile(@Param("mobile") String mobile, @Param("youkeId") String youkeId);

    @Select("SELECT id FROM t_shop_fans WHERE lastTime IS NOT NULL AND DATEDIFF(NOW(),lastTime)>#{num} and youkeId=#{youkeId}")
    List<Long> selectFansIdByLastDay(@Param("num") int num, @Param("youkeId") String youkeId);

    @Select("SELECT id FROM t_shop_fans WHERE dealNum>#{num} and youkeId=#{youkeId}")
    List<Long> selectFansIdByDealNum(@Param("num") int num, @Param("youkeId") String youkeId);

    @Select("SELECT id FROM t_shop_fans WHERE dealTotal>#{num} and youkeId=#{youkeId}")
    List<Long> selectFansIdByDealTotal(@Param("num") int num, @Param("youkeId") String youkeId);

    /**
     * 查询本周复购粉丝数量
     *
     * @return
     */
    int selectBuyBackFansNumForThisWeek(String dykId);

    /**
     * 查询上周粉丝复购数量
     *
     * @return
     */
    int selectBuyBackFansNumForLastWeek(String dykId);

    /**
     * 查询昨日粉丝复购数量
     *
     * @return
     */
    int selectBuyBackFansNumForYesterday(String dykId);

    /**
     * 查询前七天的复购粉丝数量
     *
     * @return
     */
    int selectBuyBackFansNumForSevenDays(String dykId);

    /**
     * 查询前14天复购粉丝数量
     *
     * @return
     */
    int selectBuyBackFansNumForElevenDays(String dykId);

    /**
     * 查询前七天粉丝复购率
     *
     * @return
     */
    String selectBuyBackProbabilityForSevenDays(String dykId);

    /**
     * 查询上一个七天粉丝复购率
     *
     * @param dykId
     * @return
     */
    String selectBuyBackProbabilityForElevenDays(String dykId);

    /**
     * 查询所有粉丝数量
     *
     * @param dykId
     * @return
     */
    int queryForCount(String dykId);

    /**
     * 根据手机号码查询是否存在对应粉丝
     *
     * @param mobile
     * @param dykId
     * @return
     */
    int selectShopFansByMobile(@Param("mobile") String mobile,@Param("dykId") String dykId);

    @Select("select id from t_shop_fans where nickname = #{nickName} and youkeId = #{youkeId}")
    Integer selectByNickName(@Param("nickName") String nickName, @Param("youkeId") String youkeId);

    @Select("select * from t_shop_fans where mobile=#{mobile} and shopId=#{shopId}")
    TShopFans selectbyMobileAndShopId(@Param("mobile")String mobile, @Param("shopId")Integer shopId);

    List<Long> selectTagFilterFansList(MassSMSParamVo params);

    @Select("SELECT nickname FROM t_shop_fans WHERE mobile=#{mobile} AND youkeId=#{youkeId}")
    String selectNickNameByMobileAndYoukeId(@Param("mobile")String mobile, @Param("youkeId")String youkeId);

    void bacthupdateState(@Param("ids") List<String> ids, @Param("state") int state);
}