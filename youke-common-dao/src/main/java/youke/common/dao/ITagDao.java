package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TTag;
import youke.common.model.vo.result.TagVo;

import java.util.List;

public interface ITagDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TTag record);

    TTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTag record);

    /**
     * 通过标签标题和appid获取对应的标签
     *
     * @param title
     * @return
     */
    TTag selectTagByTitleAndAppId(@Param("title") String title, @Param("appId") String appId);

    /**
     * 删除对应appid和id的标签
     *
     * @param id
     * @param appId
     */
    int deleteTagByIdANdAppId(@Param("id") Integer id, @Param("appId") String appId);

    /**
     * 解除组及标签组下标签的关系
     *
     * @param groupId
     * @param appId
     */
    void removeRelationWithGroupByGroupIdAndAppId(@Param("groupId") Integer groupId, @Param("appId") String appId);

    /**
     * 获取组下所有标签
     *
     * @param groupId
     * @param appId
     * @return
     */
    List<TTag> selectTagsByGroupIdAndAppId(@Param("groupId") Integer groupId, @Param("appId") String appId);

    /**
     * 获取所有对应appid下的标签
     *
     * @param appId
     * @return
     */
    List<TTag> selectAllTagByAppId(String appId);

    /**
     * 获取 appId对应的所有已同步至微信端的标签
     * @param appId
     * @return
     */
    List<Integer> selectAllWxTagIdByAppId(String appId);

    List<TagVo> getWxList(String appId);

    @Select("SELECT COUNT(id) FROM t_tag WHERE groupId=0 AND appId=#{appId}")
    int selectSystagCount(@Param("appId") String appId);

    @Select("select * from t_tag where groupId=#{groupId} and appId=#{appId}")
    List<TTag> selectByGroupIdAndAppId(@Param("groupId")int groupId,@Param("appId")String appId);

    @Update("update t_tag set appId=#{appId} where appId=#{dykId}")
    int updateAppIdFrom(@Param("appId")String appId, @Param("dykId")String dykId);
}