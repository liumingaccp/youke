package youke.core.scheduler.service;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IUserDao;
import youke.common.model.TUser;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AccountService extends Base {
    @Resource
    private IUserDao userDao;

    /**
     * 冻结到达过期时间的子账户
     */
    public void doCheckUserState(){
        List<Integer> ids =  userDao.selectExpSubAccount();
        System.out.println("檢索到"+ids.size()+"条数据");
        if (ids.size()>0){
            for (Integer id:ids) {
                TUser user = new TUser();
                user.setId(id);
                user.setState(1);
                userDao.updateByPrimaryKeySelective(user);
            }
        }
    }
}
