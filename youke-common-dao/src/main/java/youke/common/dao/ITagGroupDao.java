package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TTagGroup;
import youke.common.model.vo.result.TagListVo;

import java.util.List;

public interface ITagGroupDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TTagGroup record);

    TTagGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTagGroup record);

    TTagGroup selectTagGroupByTitleAndAppId(@Param("title") String title, @Param("appId") String appid);

    /**
     * 删除对应标签组
     *
     * @param id
     * @param appId
     */
    int deleteByPrimaryKeyAndAppId(@Param("id") Integer id, @Param("appId") String appId);

    /**
     * 更新标签组信息
     *
     * @param id
     * @param title
     * @param appId
     */
    int updateByGroupIdAndAppId(@Param("id") Integer id, @Param("title") String title, @Param("appId") String appId);

    /**
     * 获取所有标签
     *
     * @param appId
     * @return
     */
    List<TagListVo> getList(String appId);

    /**
     * 获取当前用户下的所有标签组
     *
     * @param appId
     * @return
     */
    int selectAllGroupByAppId(String appId);

    /**
     * 获取对应标签组
     *
     * @param groupId
     * @return
     */
    TTagGroup getGroupByGroupId(Integer groupId);

    @Update("update t_tag_group set appId=#{appId} where appId=#{dykId}")
    int updateAppIdFrom(@Param("appId")String appId, @Param("dykId")String dykId);
}