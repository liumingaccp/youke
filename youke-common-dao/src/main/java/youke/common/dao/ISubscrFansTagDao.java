package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TSubscrFansTag;

import java.util.List;

public interface ISubscrFansTagDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TSubscrFansTag record);

    TSubscrFansTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSubscrFansTag record);

    List<Integer> selectTagsIdByOpenId(String openId);

    void deleteTagsRelative(@Param("openId") String openId, @Param("tagsId") String[] split);


    /**
     * 删除对应购物粉丝的标签
     */
    int removeSubScrFansTag(@Param("tagId") Integer tagId, @Param("appId") String appId);

    /**
     * 获取appId下所有的被消费过的 tagId
     * @param appId
     * @return
     */
    List<Integer> selectAllTags(String appId);

    void updateByFansTagId(@Param("fansTagId") Integer fansTagId, @Param("type") int type);

    @Select("select id from t_subscr_fans_tag where openId = #{openId} and tagId = #{tagId}")
    Integer isRelative(@Param("openId") String openId, @Param("tagId") Integer id);
}