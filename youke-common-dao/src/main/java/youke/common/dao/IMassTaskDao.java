package youke.common.dao;

import youke.common.model.TMassTask;

public interface IMassTaskDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMassTask record);

    TMassTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMassTask record);

}