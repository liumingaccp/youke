package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TSubscrTemplate;

import java.util.List;

public interface ISubscrTemplateDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TSubscrTemplate record);

    TSubscrTemplate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSubscrTemplate record);

    @Select("select * from t_subscr_template where appId=#{appId}")
    List<TSubscrTemplate> selectTemplates(@Param("appId") String appId);

    @Delete("delete from t_subscr_template where appId=#{appId} and templateId=#{templateId}")
    int deleteTemplate(@Param("appId")String appId, @Param("templateId")String templateId);

    @Select("select templateId from t_subscr_template where appId=#{appId} and templateShort=#{templateShort} limit 1")
    String selectTemplateId(@Param("appId")String appId, @Param("templateShort")String templateShort);
}