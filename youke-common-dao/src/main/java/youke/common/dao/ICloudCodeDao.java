package youke.common.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TChatMessage;
import youke.common.model.TCloudCode;
import youke.common.model.vo.param.cloudcode.CloudCodeQueryVo;
import youke.common.model.vo.param.cloudcode.CloudCodeRecordQueryVo;
import youke.common.model.vo.result.cloudcode.CloudCodeNoticeVo;
import youke.common.model.vo.result.cloudcode.CloudCodeQueryRetVo;
import youke.common.model.vo.result.cloudcode.CloudCodeRecordQueryRetVo;

import java.util.List;

public interface ICloudCodeDao {
    int deleteByPrimaryKey(Long id);

    int insert(TCloudCode record);

    int insertSelective(TCloudCode record);

    TCloudCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCloudCode record);

    int updateByPrimaryKey(TCloudCode record);

    List<CloudCodeQueryRetVo> selectCloudCodeList(CloudCodeQueryVo params);

    List<CloudCodeRecordQueryRetVo> selectRecordList(CloudCodeRecordQueryVo params);

    @Insert("insert into t_chat_message(type,body,weixinId,friendId,createTime) values(#{type},#{body},#{weixinId},#{friendId},#{createTime})")
    void insertChatMessage(@Param("type") String type, @Param("body") String body, @Param("weixinId") String weixinId, @Param("friendId") String friendId, @Param("createTime") String createTime);

    List<TChatMessage> selectChatMessage(@Param("type") String type, @Param("sort") int sort, @Param("weixinId") String weixinId, @Param("friendId") String friendId);

    @Select("SELECT a.id, a.title, b.remark, c.totalTimes FROM t_cloud_code a INNER JOIN t_cloud_code_qrcode b ON a.id = b.cloudId INNER JOIN t_cloud_code_rule c ON b.ruleId = c.id WHERE c.totalTimes != 0 AND b.scanTimes >= c.totalTimes AND a.state = 0")
    List<CloudCodeNoticeVo> selectOverLoadCode();
}