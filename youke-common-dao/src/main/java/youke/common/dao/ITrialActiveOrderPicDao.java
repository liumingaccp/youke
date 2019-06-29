package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import youke.common.model.TTrialActiveOrderPic;

import java.util.List;

public interface ITrialActiveOrderPicDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TTrialActiveOrderPic record);

    TTrialActiveOrderPic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTrialActiveOrderPic record);

    List<String> queryListByOrderId(Long orderId);

    List<TTrialActiveOrderPic> queryPicsByOrderId(@Param("orderId") long orderId, @Param("activeId") Integer activeId);
}