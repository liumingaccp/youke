package youke.common.dao;

import youke.common.model.TAdminAgency;

public interface IAdminAgencyDao {
    int deleteByPrimaryKey(Long id);

    int insert(TAdminAgency record);

    int insertSelective(TAdminAgency record);

    TAdminAgency selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TAdminAgency record);

    int updateByPrimaryKey(TAdminAgency record);

    TAdminAgency selectByMobile(String mobile);
}