package youke.common.dao;

import youke.common.model.TWxPerCircleMarketComment;

public interface IWxPerCircleMarketCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerCircleMarketComment record);

    int insertSelective(TWxPerCircleMarketComment record);

    TWxPerCircleMarketComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerCircleMarketComment record);

    int updateByPrimaryKey(TWxPerCircleMarketComment record);
}