package youke.common.dao;

import youke.common.model.TLoginHistory;
import youke.common.model.vo.param.SubAccountLoginRecordQueryVo;
import youke.common.model.vo.result.SubAccountLoginRecordRetVo;

import java.util.List;

public interface ILoginHistoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(TLoginHistory record);

    int insertSelective(TLoginHistory record);

    TLoginHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TLoginHistory record);

    int updateByPrimaryKey(TLoginHistory record);

    List<SubAccountLoginRecordRetVo> selectRegistrationRecordList(SubAccountLoginRecordQueryVo params);
}