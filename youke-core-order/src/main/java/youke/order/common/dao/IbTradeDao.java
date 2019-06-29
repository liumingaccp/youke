package youke.order.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.order.common.model.TbTrade;
import youke.order.common.source.DataSource;

import java.util.Date;
import java.util.List;

public interface IbTradeDao {
    int deleteByPrimaryKey(Long tid);

    int insertSelective(TbTrade record);

    TbTrade selectByPrimaryKey(Long tid);

    int updateByPrimaryKeySelective(TbTrade record);

    int updateByPrimaryKeyWithBLOBs(TbTrade record);

    int updateByPrimaryKey(TbTrade record);

    @DataSource(name=DataSource.dataSource2)
    List<TbTrade> selectLastTrades(@Param("snicks")List<String> snicks,@Param("lastdate")Date lastdate);

    List<TbTrade> selectAll();
}