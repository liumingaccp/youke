package youke.web.spread.controller.interceptor;


import youke.facade.user.vo.UserVo;
import youke.web.spread.common.exception.BusinessException;
import youke.web.spread.common.redis.RedisUtil;
import youke.web.spread.common.utils.MD5Util;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 授权用户通用类
 *
 * @author liuming
 * @version 2017-12-27
 */
public class AuthUser {

    private final static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();

    public static void setRequest(HttpServletRequest request) {
        requestThreadLocal.set(request);
    }

    public static HttpServletRequest request() {
        return requestThreadLocal.get();
    }

    public static UserVo getUser() {
        String token = request().getParameter("access_token");
        if (token == null)
            throw new BusinessException("access_token is null");
        return (UserVo) RedisUtil.get(token);
    }

    public static boolean isLogin() {
        String token = request().getParameter("access_token");
        if (token == null)
            return false;
        return RedisUtil.get(token) != null;
    }

}
