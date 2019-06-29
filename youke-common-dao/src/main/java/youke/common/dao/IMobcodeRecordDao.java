package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TMobcodeRecord;
import youke.common.model.vo.param.MassQueryVo;
import youke.common.model.vo.param.MassSMSQueryVo;
import youke.common.model.vo.result.MassRecordRetVo;

import java.util.List;

public interface IMobcodeRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMobcodeRecord record);

    TMobcodeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMobcodeRecord record);

    List<TMobcodeRecord> getRecordList(MassSMSQueryVo params);

    String selectTemplateByContent(@Param("dykId") String dykId, @Param("content") String content);

    @Select("SELECT * from t_mobcode_record where youkeId = #{dykId} AND taskId = #{taskId}")
    TMobcodeRecord getRecordByYoukeIdAndTaskId(@Param("dykId") String dykId, @Param("taskId") Integer taskId);

    @Update("update t_mobcode_record set state=3,successNum=successNum+1 where id=#{id}")
    int updateStateAndSuccessNum(@Param("id") Integer id);

    @Update("update t_mobcode_record set state=4,failReason=#{failReason},failNum=failNum+1 where id=#{id}")
    int updateStateAndFailNum(@Param("id") Integer id, @Param("failReason") String report);

    @Update("UPDATE t_mobcode_record set failNum=failNum+1 where id=#{recordId}")
    int updateFailNumByRecordId(@Param("recordId") Integer recordId);

    @Select("SELECT state from t_mobcode_record where templetePro = #{templateId}")
    int selectTemplateByTemplateId(@Param("templateId") String templateId);

    List<MassRecordRetVo> selectMassList(MassQueryVo params);

    @Select("SELECT * from t_mobcode_record where templetePro = #{templateId}")
    TMobcodeRecord selectRecordByTemplateId(@Param("templateId") String templateId);

    @Select("SELECT  templetePro from t_mobcode_record where state = 5")
    List<String> selectUnExecutedTask();
}