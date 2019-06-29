package youke.common.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import youke.common.model.TSysMessage;

import java.util.List;

public interface ISysMessageDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TSysMessage record);

    TSysMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSysMessage record);

    @Insert("INSERT INTO t_sys_message(title,content,state,createTime,youkeId) VALUES(#{title},#{content},0,NOW(),#{youkeId})")
    int addMessage(@Param("title") String title, @Param("content") String content, @Param("youkeId") String youkeId);

    /**
     * 更新系统消息为已读状态
     *
     * @param id
     * @param youkeId
     * @return
     */
    int updateSystemMsgState(@Param("id") Integer id, @Param("youkeId") String youkeId);

    /**
     * 获取系统未读消息数量
     *
     * @param youkeId
     * @return
     */
    int getUnReadMsgNum(String youkeId);

    /**
     * 获取系统分页消息
     *
     * @param keyword
     * @return
     */
    List<TSysMessage> getSystemMsgList(@Param("keyword") String keyword, @Param("youkeId") String youkeId);
}