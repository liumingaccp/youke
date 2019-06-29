package youke.common.dao;

import youke.common.model.TCloudCodeRecord;

public interface ICloudCodeRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(TCloudCodeRecord record);

    int insertSelective(TCloudCodeRecord record);

    TCloudCodeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCloudCodeRecord record);

    int updateByPrimaryKey(TCloudCodeRecord record);
}