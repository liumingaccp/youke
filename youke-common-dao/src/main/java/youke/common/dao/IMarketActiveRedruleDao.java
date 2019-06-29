package youke.common.dao;

import youke.common.model.TMarketActiveRedrule;

public interface IMarketActiveRedruleDao {
    int deleteByPrimaryKey(Long activeid);

    int insertSelective(TMarketActiveRedrule record);

    TMarketActiveRedrule selectByPrimaryKey(Long activeid);

    int updateByPrimaryKeySelective(TMarketActiveRedrule record);
}