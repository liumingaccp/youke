package youke.common.dao;


import youke.common.model.TMobcodeTask;

public interface IMobcodeTaskDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMobcodeTask record);

    TMobcodeTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMobcodeTask record);
}