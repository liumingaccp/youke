package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TCollageActiveOrder;
import youke.common.model.TCutpriceActiveOrder;
import youke.common.model.vo.param.helper.CollageOrderQueryVo;
import youke.common.model.vo.result.helper.CollageOrderQueryRetVo;
import youke.common.model.vo.result.helper.WxFansLessVo;

import java.util.List;
import java.util.Map;

public interface ICollageActiveOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(TCollageActiveOrder record);

    int insertSelective(TCollageActiveOrder record);

    TCollageActiveOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCollageActiveOrder record);

    int updateByPrimaryKey(TCollageActiveOrder record);

    List<CollageOrderQueryRetVo> queryList(CollageOrderQueryVo params);

    List<WxFansLessVo> getParticipantByTuanId(@Param("appId") String appId, @Param("tuanId") Integer tuanId);

    Integer selectByOpenIdAndTuanId(@Param("openId") String openId, @Param("tuanId") Long tuanId);

    @Select("select count(id) from t_collage_active_order where appId = #{appId} and activeId = #{activeId}")
    Integer queryCountByActiveId(@Param("appId") String appId, @Param("activeId") Integer activeId);

    @Select("SELECT a.id,a.orderno FROM t_collage_active_order a INNER JOIN t_collage_active b ON a.`activeId`=b.`id` WHERE a.state=#{state} AND a.orderno IS NOT NULL AND b.waitDay=0")
    List<Map> selectOrderMapByState(@Param("state") int state);

    void updateBatchState(@Param("orderIds") List<Long> orderIds, @Param("state") int state);

    @Update("UPDATE t_collage_active_order a INNER JOIN t_collage_active b ON a.`activeId`=b.`id` SET a.state=3 WHERE a.`activeId`=b.`id` AND ABS(DATEDIFF(a.`orderTime`,NOW())) >= b.`waitDay` AND a.`state`=2")
    void updateTimeOutRebate();

    @Select("SELECT * FROM t_collage_active_order WHERE state=#{state}")
    List<TCollageActiveOrder> selectOrderByState(@Param("state") int state);

    void updateStateForBeg(@Param("ids")List<Long> ids);

    List<String> selectOpenIdByTuanId(@Param("tuanId") Long tuanId, @Param("appId") String appId);

    @Select("select id from t_collage_active_order where orderno = #{orderNo} AND appId = #{appId}")
    Integer selectByOrderNo(@Param("orderNo") String orderNo, @Param("appId") String appId);

    @Update("UPDATE t_collage_active_order set state=#{state},backTime=NOW() where id=#{orderId}")
    int updateState(@Param("orderId") Long orderId, @Param("state") int state);

    @Update("update t_collage_active_order set state=1 where appId=#{appId} and tuanId=#{tuanId}")
    void updateStateByTuanId(@Param("appId") String appid, @Param("tuanId") Long id);

    @Select("select * from t_collage_active_order where openId = #{openId} and tuanId = #{tuanId}")
    TCollageActiveOrder selectOrderByOpenIdAndTuanId(@Param("openId") String openId, @Param("tuanId") Integer tuanId);
}