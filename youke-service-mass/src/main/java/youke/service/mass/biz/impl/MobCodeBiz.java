package youke.service.mass.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.dao.IMobcodeDao;
import youke.common.dao.IMobcodeOrderDao;
import youke.common.dao.IMobcodePackageDao;
import youke.common.model.TMobcode;
import youke.common.model.TMobcodeOrder;
import youke.common.model.TMobcodePackage;
import youke.common.model.vo.param.QueryObjectVO;
import youke.service.mass.biz.IMobCodeBiz;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MobCodeBiz implements IMobCodeBiz {
    @Resource
    private IMobcodeDao mobcodeDao;
    @Resource
    private IMobcodePackageDao mobcodePackageDao;
    @Resource
    private IMobcodeOrderDao mobcodeOrderDao;

    public Integer getCodeCount(String dykId) {
        TMobcode mobcode = mobcodeDao.selectByPrimaryKey(dykId);
        if (mobcode != null) {
            return mobcode.getCount();
        } else {
            return 0;
        }
    }

    public List<TMobcodePackage> getPackages() {
        return mobcodePackageDao.getPackages();
    }

    public PageInfo<TMobcodeOrder> getOrderList(QueryObjectVO params, String dykId) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<TMobcodeOrder> list = mobcodeOrderDao.getOrderList(dykId);
        return new PageInfo<>(list);
    }
}
