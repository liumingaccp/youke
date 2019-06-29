package youke.common.dao;

import youke.common.model.TWxPerAutoAddedRecord;

public interface IWxPerAutoAddedRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerAutoAddedRecord record);

    int insertSelective(TWxPerAutoAddedRecord record);

    TWxPerAutoAddedRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerAutoAddedRecord record);

    int updateByPrimaryKey(TWxPerAutoAddedRecord record);
}