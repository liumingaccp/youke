package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TMarketActivePic;

import java.util.List;

public interface IMarketActivePicDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TMarketActivePic record);

    TMarketActivePic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TMarketActivePic record);

    @Select("select id, title, picUrl, activeId, type, youkeId from t_market_active_pic where activeId = #{activeId} AND youkeId = #{dykId}")
    List<TMarketActivePic> selectPicByActiveId(@Param("activeId") Long activeId, @Param("dykId") String dykId);

    @Select("select id, title, picUrl, activeId, type, youkeId from t_market_active_pic where activeId = #{activeId}")
    List<TMarketActivePic> selectPicsByActiveId(Long activeId);

    @Delete("delete from t_market_active_pic where activeId = #{activeId}")
    void deletePic(Long activeId);
}