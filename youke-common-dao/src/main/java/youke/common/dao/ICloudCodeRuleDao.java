package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TCloudCodeRule;
import youke.common.model.vo.result.cloudcode.CloudCodeQrCodeAuditRetVo;
import youke.common.model.vo.result.cloudcode.CloudCodeRuleAuditRetVo;

import java.util.List;

public interface ICloudCodeRuleDao {
    int deleteByPrimaryKey(Long id);

    int insert(TCloudCodeRule record);

    int insertSelective(TCloudCodeRule record);

    TCloudCodeRule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCloudCodeRule record);

    int updateByPrimaryKey(TCloudCodeRule record);

    @Delete("delete from t_cloud_code_rule where cloudId=#{cloudId}")
    int deleteByCloudCodeId(@Param("cloudId") Long cloudId);

    @Select("SELECT * from t_cloud_code_rule where cloudId=#{cloudId}")
    List<TCloudCodeRule> selectByCloudId(@Param("cloudId") Long id);

    List<CloudCodeQrCodeAuditRetVo> selectByRuleId(@Param("id") Long id);

    List<CloudCodeRuleAuditRetVo> selectRulesAndQrCodesByCloudId(Long id);
}