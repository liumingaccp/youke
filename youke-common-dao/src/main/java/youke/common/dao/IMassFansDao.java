package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import youke.common.model.TMassFans;
import youke.common.model.vo.param.MassFansQueryVo;

import java.util.List;

public interface IMassFansDao {
    int deleteByPrimaryKey(String openid);

    int insertSelective(TMassFans record);

    TMassFans selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(TMassFans record);

    List<TMassFans> queryList(MassFansQueryVo params);

    List<TMassFans> querySubList(MassFansQueryVo params);

    long querySubCount(MassFansQueryVo params);

    long queryCount(MassFansQueryVo params);

    String checkExite(String openid);

    void updateNumForOpendIds(@Param("openIds") List<String> openIds);
}