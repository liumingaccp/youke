package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TFriendTag;
import youke.common.model.vo.result.FriendIdVo;
import youke.common.model.vo.result.TagVo;

import java.util.List;

public interface IFriendTagDao {
    int deleteByPrimaryKey(Long id);

    int insert(TFriendTag record);

    TFriendTag selectByPrimaryKey(Long id);

    int updateByPrimaryKey(TFriendTag record);

    @Select("SELECT b.id,b.title FROM t_friend_tag a INNER JOIN t_tag b ON a.`tagId`=b.`id` WHERE a.`friendId`=#{friendId} and a.youkeId=#{youkeId} and a.weixinId=#{weixinId}")
    List<TagVo> selectFriendTags(@Param("friendId")String friendId, @Param("youkeId")String youkeId, @Param("weixinId")String weixinId);

    @Select("select count(1) from t_friend_tag where tagId=#{tagId} and friendId=#{friendId}")
    int selectCount(@Param("tagId")Integer tagId, @Param("friendId")String friendId);

    List<FriendIdVo> selectTagFriendIds(@Param("tagIds")String[] tagIds, @Param("weixinId")String weixinId, @Param("youkeId")String youkeId);

    @Select("SELECT b.id,b.title FROM t_friend_tag a INNER JOIN t_tag b ON a.`tagId`=b.`id` WHERE a.weixinId=#{weixinId} GROUP BY b.id,b.`title`")
    List<TagVo> selectWeixinTags(@Param("weixinId")String weixinId);

    @Delete("delete from t_friend_tag where tagId=#{tagId} and friendId=#{friendId} and weixinId=#{weixinId} and youkeId=#{youkeId}")
    int delFriendTag(@Param("tagId")Integer tagId, @Param("friendId")String friendId, @Param("weixinId")String weixinId, @Param("youkeId")String youkeId);
}