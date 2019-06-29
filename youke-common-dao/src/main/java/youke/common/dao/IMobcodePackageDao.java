package youke.common.dao;


import youke.common.model.TMobcodePackage;

import java.util.List;

public interface IMobcodePackageDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMobcodePackage record);

    TMobcodePackage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMobcodePackage record);

    /**
     * 获取短信套餐
     *
     * @return
     */
    List<TMobcodePackage> getPackages();
}