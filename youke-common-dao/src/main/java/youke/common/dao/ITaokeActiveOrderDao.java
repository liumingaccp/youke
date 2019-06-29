package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TTaokeActiveOrder;
import youke.common.model.TTrialActiveOrder;
import youke.common.model.vo.param.helper.H5TaokeOrderQueryVo;
import youke.common.model.vo.param.helper.TaokeOrderExamineParam;
import youke.common.model.vo.param.helper.TaokeOrderQueryVo;
import youke.common.model.vo.result.ActivePayVo;
import youke.common.model.vo.result.helper.H5TaokeOrderDetailQueryRetVo;
import youke.common.model.vo.result.helper.H5TaokeOrderQueryRetVo;
import youke.common.model.vo.result.helper.TaokeOrderDetailRetVo;
import youke.common.model.vo.result.helper.TaokeOrderQueryRetVo;

import java.util.List;
import java.util.Map;

public interface ITaokeActiveOrderDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TTaokeActiveOrder record);

    TTaokeActiveOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTaokeActiveOrder record);

    List<TaokeOrderQueryRetVo> queryList(TaokeOrderQueryVo params);

    TaokeOrderDetailRetVo selectExamineDetail(@Param("id") Integer orderId, @Param("appId") String appId);

    void updateStateBatch(TaokeOrderExamineParam param);

    @Select("select orderno from t_taoke_active_order where buyerOpenId = #{buyerOpenId} and activeId = #{activeId}")
    String selectByOpenId(@Param("buyerOpenId") String buyerOpenId, @Param("activeId") Integer activeId);

    @Select("select COUNT(id) from t_taoke_active_order where buyerOpenId = #{buyerOpenId} and activeId = #{activeId}")
    Long selectIdByOpenId(@Param("buyerOpenId") String buyerOpenId, @Param("activeId") Integer activeId);

    Integer queryCountForOpenId(H5TaokeOrderQueryVo params);

    List<H5TaokeOrderQueryRetVo> queryOrderListByOenId(H5TaokeOrderQueryVo params);

    List<H5TaokeOrderDetailQueryRetVo> queryListByTaokeOpenId(@Param("openId") String openId, @Param("activeId") Integer activeId);

    @Select("SELECT active.goodsId FROM t_taoke_active_order torder JOIN t_taoke_active active ON torder.activeId = active.id WHERE torder.id = #{orderId}")
    String selectGoodId(Long orderId);

    @Select("select COUNT(id) from t_taoke_active_order where orderno = #{orderNo} and youkeId = #{dyk}")
    Integer selectCount(@Param("orderNo") String orderNo, @Param("dyk") String dyk);

    @Select("SELECT active.goodsId,active.fansLimit, active.commision, active.openBackLimit ,torder.state,torder.buyerOpenId  FROM t_taoke_active_order torder JOIN t_taoke_active active ON torder.activeId = active.id WHERE torder.id = #{orderId}")
    Map<String,Object> selectOrderDetail(Long orderId);

    int updateBatchState(@Param("orderIds")List<Long> orderIds, @Param("state")int state);

    @Select("SELECT id,orderno FROM t_taoke_active_order WHERE state=#{state} AND orderno IS NOT NULL")
    List<Map> selectOrderMapByState(@Param("state") int state);

    @Select("SELECT * FROM t_taoke_active_order WHERE state=#{state}")
    List<TTaokeActiveOrder> selectOrderByState(@Param("state") int state);

    @Update("UPDATE t_taoke_active_order set state=#{state},backTime=NOW() where id=#{orderId}")
    int updateState(@Param("orderId")Long orderId, @Param("state")int state);

    @Update("UPDATE t_taoke_active_order a INNER JOIN t_taoke_active b ON a.`activeId`=b.`id` SET a.state=4 WHERE a.`activeId`=b.`id` AND TIMESTAMPDIFF(DAY,a.`orderTime`,NOW())>=b.`waitDay` AND a.`state`=3")
    int updateAutoExamineState();

    @Select("SELECT SUM(backMoney+commision) FROM t_taoke_active_order WHERE backMoney IS NOT NULL AND state=7 AND youkeId=#{youkeId}")
    Integer selectTotalFailMoney(@Param("youkeId") String youkeId);

    @Select("SELECT id,taokeOpenId AS tOpenId, buyerOpenId AS openId, #{comeType} AS comeType,backMoney AS money, commision, appId,youkeId FROM t_taoke_active_order  WHERE state=7 AND youkeId=#{youkeId}")
    List<ActivePayVo> selectActivePayVo(@Param("comeType") int comeType,@Param("youkeId") String youkeId);

    H5TaokeOrderQueryRetVo selectOrderInfo(@Param("appId") String appId, @Param("openId") String openId, @Param("orderId") long orderId);
}