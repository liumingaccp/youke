package youke.common.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TAdminMessage;

public interface IAdminMessageDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TAdminMessage record);

    TAdminMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TAdminMessage record);

    @Insert("insert into t_admin_message(type,content,youkeId,createTime) values(#{type},#{content},#{youkeId},NOW())")
    int addMessage(@Param("type")String type,@Param("content")String content,@Param("youkeId")String youkeId);
}