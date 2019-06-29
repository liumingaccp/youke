package youke.facade.user.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.TUser;
import youke.facade.user.vo.Body;

public interface IDemoService {
	
	String getUser(int id);

	PageInfo<TUser> getUsers();

}
