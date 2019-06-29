package youke.common.dao;

import org.apache.ibatis.annotations.Update;
import youke.common.model.TWxPerCircleMarketTask;

public interface IWxPerCircleMarketTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerCircleMarketTask record);

    int insertSelective(TWxPerCircleMarketTask record);

    TWxPerCircleMarketTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerCircleMarketTask record);

    int updateByPrimaryKey(TWxPerCircleMarketTask record);

    @Update("update t_wxper_circlemarket_task set state = 3 where state = 1 and abs(TIMESTAMPDIFF(MINUTE,pushTime,NOW())) > 5")
    void updateUnReturn();
}