package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import youke.common.model.TWxperFansTag;

import java.util.HashMap;
import java.util.List;

public interface IWxPerFansTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TWxperFansTag record);

    int insertSelective(TWxperFansTag record);

    TWxperFansTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TWxperFansTag record);

    int updateByPrimaryKey(TWxperFansTag record);

    List<HashMap<String,Object>> queryTagListByFansId(@Param("fansId") Long fansId, @Param("appId") String appId, @Param("youkeId") String youkeId);

    List<Long> selectByYouke(@Param("youkeId") String youkeId);

    List<Long> selectByTagId(@Param("tagid") Long tagId, @Param("youkeId") String youkeId);

    void saveBacth(@Param("list") List<TWxperFansTag> leftFansTagList);

    void deleteBacth(@Param("tagList") List<Long> tagList, @Param("fansId") Long fansId, @Param("youkeId") String youkeId);
}