package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import youke.common.model.TMobcodeTemplete;
import youke.common.model.vo.param.MassSMSQueryVo;

import java.util.List;

public interface IMobcodeTempleteDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMobcodeTemplete record);

    TMobcodeTemplete selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMobcodeTemplete record);

    /**
     * 分页获取模板列表
     *
     * @param params
     * @return
     */
    List<TMobcodeTemplete> getTemplateList(MassSMSQueryVo params);

    /**
     * 获取模板列表
     *
     * @param params
     * @return
     */
    List<String> getTemplateNames(MassSMSQueryVo params);

    /**
     * 查询对应模板名称的模板个数
     * @param name
     * @return
     */
    int selectTemplateByName(@Param("name") String name,@Param("dykId") String dykId);
}