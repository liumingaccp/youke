package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TWxPerAccount;

public interface IWxPerAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerAccount record);

    int insertSelective(TWxPerAccount record);

    TWxPerAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerAccount record);

    int updateByPrimaryKey(TWxPerAccount record);

    @Select("SELECT * from t_wxper_account where wx = #{wechatnum} AND state = 0")
    TWxPerAccount selectAccountByWeChat(@Param("wechatnum") String wechatnum);

    @Update("UPDATE t_wxper_account set state = #{state} where id  = #{accountId}")
    int updateStateByPrimaryKey(@Param("accountId") Long accountId, @Param("state") int state);
}