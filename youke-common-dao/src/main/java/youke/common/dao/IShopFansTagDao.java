package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TShopFansTag;

import java.util.List;

public interface IShopFansTagDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TShopFansTag record);

    TShopFansTag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TShopFansTag record);

    void delTags(@Param("fansId") long fansId, @Param("tags") String[] tags);

    List<Integer> selectByFansId(long fansId);

    /**
     * 删除对应用户管理的粉丝标签
     *
     * @param youkeId 管理用户id
     * @param tagId   标签id
     * @return
     */
    int removeShopFansTag(@Param("tagId") Integer tagId, @Param("youkeId") String youkeId);

    @Select("select count(id) from t_shop_fans_tag where fansId=#{fansId} and tagId=#{tagId} and youkeId=#{youkeId}")
    int selectCount(@Param("fansId")Long fansId, @Param("tagId")Integer tagId, @Param("youkeId")String youkeId);

    @Delete("DELETE FROM t_shop_fans_tag  WHERE fansId=#{fansId} AND youkeId=#{youkeId} AND tagId IN(SELECT tagId FROM t_tag_rule WHERE appId=#{appId} AND `type`=#{type})")
    int deleteSameTags(@Param("fansId")Long fansId, @Param("type")Integer type, @Param("appId")String appId, @Param("youkeId")String youkeId);
}