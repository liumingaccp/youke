package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TCutpriceActive;
import youke.common.model.vo.param.helper.CutPriceActiveQueryVo;
import youke.common.model.vo.result.helper.CutPriceActiveRetVo;

import java.util.List;

public interface ICutpriceActiveDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TCutpriceActive record);

    int insertSelective(TCutpriceActive record);

    TCutpriceActive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCutpriceActive record);

    int updateByPrimaryKey(TCutpriceActive record);

    List<CutPriceActiveRetVo> selectActiveList(CutPriceActiveQueryVo params);

    @Select("SELECT * from t_cutprice_active where appId = #{appId} and state=1 ORDER BY createTime DESC")
    List<TCutpriceActive> selectActiveListByAppId(@Param("appId") String appId);

    @Update("UPDATE t_cutprice_active SET state=1 WHERE NOW() BETWEEN startTime AND endTime AND state=0")
    int updateStateIng();

    @Update("UPDATE t_cutprice_active SET state=2 WHERE NOW() > endTime AND state<2")
    int updateStateEnd();

    @Select("select count(id) from t_cutprice_active where appId = #{appId}")
    Long selectActiveCount(String appId);
}