package youke.common.dao;

import youke.common.model.TMarketActiveLuck;

public interface IMarketActiveLuckDao {
    int deleteByPrimaryKey(Long activeid);

    int insertSelective(TMarketActiveLuck record);

    TMarketActiveLuck selectByPrimaryKey(Long activeid);

    int updateByPrimaryKeySelective(TMarketActiveLuck record);
}