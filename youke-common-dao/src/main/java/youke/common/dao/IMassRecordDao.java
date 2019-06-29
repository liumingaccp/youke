package youke.common.dao;

import youke.common.model.TMassRecord;
import youke.common.model.vo.param.MassFansQueryVo;
import youke.common.model.vo.result.MassRecordVo;

import java.util.List;

public interface IMassRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMassRecord record);

    TMassRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMassRecord record);

    List<MassRecordVo> queryList(MassFansQueryVo params);

    TMassRecord selectByTaskId(int taskId);
}