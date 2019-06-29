package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TSubscrFansIntegral;
import youke.common.model.vo.param.WealIntegralQueryVo;
import youke.common.model.vo.result.SubFansIntegralDetailVo;
import youke.common.model.vo.result.SubFansIntegralVo;

import java.util.List;

public interface ISubscrFansIntegralDao {

    int deleteByPrimaryKey(Long id);

    int insertSelective(TSubscrFansIntegral record);

    TSubscrFansIntegral selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSubscrFansIntegral record);

    @Select("select sum(integral) from t_subscr_fans_integral where openId=#{openId}")
    Integer selectSumIntegral(@Param("openId") String openId);

    @Select("select * from t_subscr_fans_integral where openId=#{openId} order by createTime desc")
    List<TSubscrFansIntegral> selectIntegralList(@Param("openId") String openId);

    List<SubFansIntegralVo> queryList(WealIntegralQueryVo params);

    List<SubFansIntegralDetailVo> queryListByOpenId(String openId);

    Long selectCount(WealIntegralQueryVo params);
}