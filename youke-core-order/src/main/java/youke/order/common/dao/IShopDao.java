package youke.order.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.order.common.model.TShop;
import youke.order.common.source.DataSource;

import java.util.List;
import java.util.Map;

public interface IShopDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TShop record);

    TShop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TShop record);

    @Select("SELECT dianName FROM t_shop WHERE state=1 AND `type`<2 AND isDemo=0")
    List<String> selectDianNames();

    @Select("select * from t_shop where dianName=#{sellernick} limit 1")
    TShop selectByDianName(@Param("sellernick") String sellernick);
}