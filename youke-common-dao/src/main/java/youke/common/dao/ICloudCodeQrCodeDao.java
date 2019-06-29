package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TCloudCodeQrCode;
import youke.common.model.vo.result.cloudcode.H5CloudCodeQrCodeRetVo;

import java.util.List;

public interface ICloudCodeQrCodeDao {
    int deleteByPrimaryKey(Long id);

    int insert(TCloudCodeQrCode record);

    int insertSelective(TCloudCodeQrCode record);

    TCloudCodeQrCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCloudCodeQrCode record);

    int updateByPrimaryKey(TCloudCodeQrCode record);

    @Delete("delete from t_cloud_code_qrcode where cloudId=#{cloudId}")
    int deleteByCloudCodeId(@Param("cloudId") Long cloudId);

    @Select("SELECT * from t_cloud_code_qrcode where cloudId=#{id}")
    List<TCloudCodeQrCode> selectByCloudId(@Param("id") Long id);

    List<Long> selectFilterQrCodes(@Param("id") Long aLong, @Param("ruleId") Long id, @Param("dayTimes") Integer dayTimes, @Param("totalTimes") Integer totalTimes);

    @Update("UPDATE t_cloud_code_qrcode SET scanTimes=scanTimes+1, lastScanTime=NOW() WHERE id = #{codeId}")
    int updateScanTimesAndLastScanTime(@Param("codeId") Long id);

    int updateInvalidCode(@Param("codeIds") List<Long> codeIds, @Param("dykId") String dykId, @Param("cloudId") Long cloudId);

    @Select("SELECT id from t_cloud_code_qrcode where cloudId=#{cloudId} AND ruleId=#{ruleId} ORDER BY lastScanTime DESC limit 1")
    Long selectLastScanQrCodeId(@Param("cloudId") Long cloudId, @Param("ruleId") Long ruleId);

    @Select("SELECT id, url, remark from t_cloud_code_qrcode where id=#{codeId}")
    H5CloudCodeQrCodeRetVo selectCodeByPrimary(Long codeId);
}