package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TArea;
import youke.common.model.vo.result.NewsVo;
import youke.common.model.vo.result.TagVo;

import java.util.List;

public interface IAreaDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TArea record);

    TArea selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TArea record);

    List<TArea> selectByPid(int pid);

    @Select("select id,title from t_admin_news_type where state=0")
    List<TagVo> selectNewsType();

    @Select("SELECT a.id,a.title,a.cover,a.intro,a.createTime,a.typeId,b.title AS typeTitle FROM t_admin_news_body a INNER JOIN t_admin_news_type b ON a.`typeId`=b.`id`  WHERE a.`createTime`<NOW() and a.`typeId`<9")
    List<NewsVo> selectNewsList();

    @Select("SELECT a.id,a.title,a.cover,a.intro,a.createTime,a.typeId,b.title AS typeTitle FROM t_admin_news_body a INNER JOIN t_admin_news_type b ON a.`typeId`=b.`id` where a.typeId=#{typeId}  and a.`createTime`<NOW()")
    List<NewsVo> selectNewsListByTypeId(@Param("typeId") int typeId);

    @Select("SELECT id,title,cover,intro,content,createTime,typeId FROM t_admin_news_body WHERE id=#{id}")
    NewsVo selectNewsInfo(@Param("id")int id);

}