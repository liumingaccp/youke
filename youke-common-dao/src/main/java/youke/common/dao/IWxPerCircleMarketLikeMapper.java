package youke.common.dao;

import youke.common.model.TWxPerCircleMarketLike;

public interface IWxPerCircleMarketLikeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerCircleMarketLike record);

    int insertSelective(TWxPerCircleMarketLike record);

    TWxPerCircleMarketLike selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerCircleMarketLike record);

    int updateByPrimaryKey(TWxPerCircleMarketLike record);
}