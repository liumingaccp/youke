package youke.common.dao;

import youke.common.model.TSubscrKefu;

import java.util.List;

public interface ISubscrKefuDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TSubscrKefu record);

    TSubscrKefu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSubscrKefu record);

    List<TSubscrKefu> selectAll(String appId);
}