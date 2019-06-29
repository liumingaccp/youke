package youke.web.pc.interceptor;

import youke.common.exception.BusinessException;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.MD5Util;
import youke.facade.user.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 授权用户通用类
 *
 * @author liuming
 * @version 2017-12-27
 */
public class AuthUser {

    private final static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();

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
        return (UserVo) RedisSlaveUtil.get(token);
    }

    public static String setUser(UserVo user) {
        String token = MD5Util.MD5(UUID.randomUUID().toString()).toUpperCase();
        RedisUtil.set(token, user, 86400); //24小时内有效
        return token;
    }

    public static boolean isLogin() {
        String token = request().getParameter("access_token");
        if (token == null)
            return false;
        return RedisSlaveUtil.get(token) != null;
    }

    public static void removeUser() {
        String token = request().getParameter("access_token");
        if (token == null)
            throw new BusinessException("access_token is null");
        RedisUtil.del(token);
    }

    public static void saveUser(UserVo user) {
        String token = request().getParameter("access_token");
        RedisUtil.set(token, user, 86400); //24小时内有效
    }
}
