package youke.service.user.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.dao.IAreaDao;
import youke.common.model.TArea;
import youke.service.user.biz.IAreaBiz;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/23
 * Time: 9:04
 */
@Service
public class AreaBiz implements IAreaBiz {

    @Resource
    private IAreaDao areaDao;

    public List<TArea> getAreas(int pid) {
        return areaDao.selectByPid(pid);
    }
}
