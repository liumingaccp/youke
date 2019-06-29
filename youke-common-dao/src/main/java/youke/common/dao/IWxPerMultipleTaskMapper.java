package youke.common.dao;

import youke.common.model.TWxPerMultipleTask;

public interface IWxPerMultipleTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerMultipleTask record);

    int insertSelective(TWxPerMultipleTask record);

    TWxPerMultipleTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerMultipleTask record);

    int updateByPrimaryKey(TWxPerMultipleTask record);
}