package youke.common.dao;

import youke.common.model.TYoukeBank;
import youke.common.model.vo.param.AccountRecordQueryVo;

import java.util.List;

public interface IYoukeBankDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TYoukeBank record);

    TYoukeBank selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TYoukeBank record);

    List<TYoukeBank> selectBankList(AccountRecordQueryVo params);
}