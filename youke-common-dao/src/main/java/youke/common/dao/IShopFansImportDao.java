package youke.common.dao;

import youke.common.model.TShopFansImport;

import java.util.List;

public interface IShopFansImportDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TShopFansImport record);

    TShopFansImport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TShopFansImport record);

    List<TShopFansImport> selectAll(String youkeId);
}