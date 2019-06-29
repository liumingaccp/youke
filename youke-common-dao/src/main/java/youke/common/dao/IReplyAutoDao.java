package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import youke.common.model.TReplyAuto;

import java.util.List;
import java.util.Map;

public interface IReplyAutoDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TReplyAuto record);

    TReplyAuto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TReplyAuto record);

    int updateByPrimaryKeyWithBLOBs(TReplyAuto record);

    int updateByPrimaryKey(TReplyAuto record);

    void deleteByAppid(@Param("appId") String appId, @Param("type") int type);

    TReplyAuto selectByAppid(@Param("appId") String appId, @Param("type") int type);

    List<Integer> selectMaterialByAppId(@Param("appId") String appId, @Param("materType") String materType);
}