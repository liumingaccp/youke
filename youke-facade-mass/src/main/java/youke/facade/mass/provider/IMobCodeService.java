package youke.facade.mass.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.TMobcodeOrder;
import youke.common.model.TMobcodePackage;
import youke.common.model.vo.param.QueryObjectVO;

import java.util.List;

public interface IMobCodeService {

    /**
     * 获取剩余短信数量
     *
     * @param dykId
     * @return
     */
    Integer getCodeCount(String dykId);

    /**
     * 获取短信充值套餐
     *
     * @return
     */
    List<TMobcodePackage> getPackages();

    /**
     * 获取充值记录
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<TMobcodeOrder> getOrderList(QueryObjectVO params, String dykId);

}
