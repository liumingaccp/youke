package youke.service.user.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.model.TUser;
import youke.facade.user.provider.IDemoService;
import youke.service.user.biz.IUserBiz;

import javax.annotation.Resource;

@Service
public class DemoService implements IDemoService {

	@Resource
	private IUserBiz userBiz;

	public String getUser(int id) {
		TUser user = userBiz.getUser(id);
	    return user.getMobile()+":"+user.getMobile();
	}

	public PageInfo<TUser> getUsers() {
		return userBiz.getUsers(1);
	}


}
