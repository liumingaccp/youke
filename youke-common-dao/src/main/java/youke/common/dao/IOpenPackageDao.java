package youke.common.dao;

import org.apache.ibatis.annotations.Select;
import youke.common.model.TOpenPackage;

import java.util.List;

public interface IOpenPackageDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TOpenPackage record);

    TOpenPackage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOpenPackage record);

    @Select("select * from t_open_package")
    List<TOpenPackage> selectPackages();
}