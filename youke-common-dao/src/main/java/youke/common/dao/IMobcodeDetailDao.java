package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TMobcodeDetail;
import youke.common.model.vo.param.MassRecordDetailQueryVo;

import java.util.List;

public interface IMobcodeDetailDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TMobcodeDetail record);

    TMobcodeDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TMobcodeDetail record);

    List<TMobcodeDetail> getRecordDetailListByRecordIdAndDykId(MassRecordDetailQueryVo params);

    int updateStateByMobileAndRecordIdWithSuccess(@Param("address") String address, @Param("recordId") Integer recordId, @Param("dykId") String dykId);

    int updateStateByMobileAndRecordIdWithFailure(@Param("address") String address, @Param("recordId") Integer recordId, @Param("dykId") String dykId);

    TMobcodeDetail getRecordDetailByRecordIdAndDykId(@Param("recordId") Integer recordId, @Param("dykId") String dykId, @Param("mobile") String mobile);

    @Select("SELECT * from t_mobcode_detail WHERE (UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(sendTime))>=800 AND state = 0 AND sendTime IS NOT NULL")
    List<TMobcodeDetail> selectFailSend();
}