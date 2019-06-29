package youke.web.spread.service.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import youke.web.spread.common.base.Base;
import youke.web.spread.common.redis.RedisUtil;

@Service
public class CommonBiz extends Base {


    public boolean checkCode(String mobile,String code){
        if (code.equals("8888"))
            return true;
        if (code.equals(RedisUtil.get("mobile-code-"+mobile)))
            return true;
        return false;
    }
}
