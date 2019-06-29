package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TShop;
import youke.common.model.vo.result.ShopVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IShopDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TShop record);

    TShop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TShop record);

    @Select("select id,title,state from t_shop where youkeId=#{youkeId}")
    List<ShopVo> selectShopListByYoukeId(@Param("youkeId") String youkeId);

    @Select("select * from t_shop where dianId=#{dianId} and type=#{type}")
    TShop selectByDianId(@Param("dianId") String dianId, @Param("type") int type);

    @Select("select refreshToken from t_shop where id=#{id}")
    String selectRefreshToken(@Param("id") Integer id);

    @Update("update t_shop set refreshToken=#{refreshToken},updateTime=NOW() where id=#{shopId}")
    int updateRefreshToken(@Param("shopId") Integer shopId, @Param("refreshToken") String refreshToken);

    List<TShop> selectAuthShops(@Param("title") String title, @Param("state") int state, @Param("type") int type, @Param("youkeId") String youkeId);

    @Select("select id, dianId,type,updateTime,youkeId from t_shop")
    List<TShop> getList();

    @Select("select id, dianId,type,updateTime,youkeId from t_shop WHERE state=1 AND `type`>1 AND isDemo=0")
    List<TShop> selectRealNOTBShops();

    @Update("update t_shop set state=2 where expTime<#{date} and youkeId=#{dykId}")
    int updateExpState(@Param("dykId") String dykId, @Param("date") Date date);

    @Update("update t_shop set accessToken=#{token} where id=#{shopId}")
    int updateAccessToken(@Param("shopId") int shopId, @Param("token") String token);

    @Select("select type from t_shop where id = #{shopid}")
    Integer selectShopTypeByShopId(@Param("shopid") String shopid);

    @Select("select * from t_shop")
    List<TShop> selectShops();

    @Update("update t_shop set state=2 where expTime is not null and expTime<Now()")
    int updateExpireState();

    @Select("SELECT a.title,a.expTime,b.followOpenId,b.mobile,a.youkeId FROM t_shop a INNER JOIN t_user b ON a.`youkeId`=b.youkeId WHERE b.role=0 AND a.state=1 AND a.expTime BETWEEN #{date1} AND #{date2}")
    List<Map> selectExpireUser(@Param("date1") Date date1, @Param("date2") Date date2);

    @Select("SELECT title from t_shop where id=#{shopid}")
    String selectTitle(@Param("shopid") Integer shopid);

    @Select("SELECT youkeId from t_shop WHERE expTime BETWEEN '2019-01-01 14:02:00' AND '2019-01-01 14:02:59'")
    List<String> selectExpireYoukeIds(@Param("date1") String date1, @Param("date2") String date2);

    @Select("SELECT IFNULL(count(id),0) from t_shop where youkeId=#{dykId} and state = 1")
    int selectShopNum(@Param("dykId") String dykId);

    @Select("SELECT IFNULL(count(id),0) from t_shop where youkeId=#{dykId}")
    int selectBindShopNum(@Param("dykId") String dykId);

    @Select("SELECT id from t_shop where youkeId = #{youkeId}")
    List<Integer> selectShopIdsByYoukeId(@Param("youkeId") String youkeId);

    @Select("SELECT state from t_shop where id = #{shopId}")
    int selectShopStateByShopId(@Param("shopId") Integer shopId);

    @Select("SELECT accessToken from t_shop where id = #{shopId}")
    String selectAccessTokenByPrimaryKey(@Param("shopId") Integer shopId);
}