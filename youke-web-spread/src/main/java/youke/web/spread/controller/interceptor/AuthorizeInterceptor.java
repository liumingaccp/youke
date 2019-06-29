package youke.web.spread.controller.interceptor;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import youke.facade.user.vo.UserVo;
import youke.web.spread.bean.JsonResult;
import youke.web.spread.common.conts.ApiCodeConstant;
import youke.web.spread.common.redis.RedisUtil;
import youke.web.spread.common.utils.AESUtil;
import youke.web.spread.common.utils.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liuming
 */
@Service
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    private static final ThreadLocal parameterJson = new ThreadLocal();

    public static void setParameterJson(String dataSourceType) {
        parameterJson.set(dataSourceType);
    }

    public static String getParameterJson() {
        return (String) parameterJson.get();
    }

    public void destroy() {}

    public void init() {}


    @Autowired
    private RedisTemplate redisTemplate;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        AuthUser.setRequest(request);
        String path = request.getServletPath();
        System.out.println("----------------------------------");
        System.out.println(path+(request.getQueryString()==null?"":"?"+request.getQueryString()));
        String jsonStr = RequestUtil.getParamsJsonStr(request);
        if("aes".equals(request.getParameter("encrypt"))){
            try {
                jsonStr = AESUtil.aesDecrypt(jsonStr);
            } catch (Exception e) {
                simpleRender(response,new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"无效参数,禁止访问"));
                return false;
            }
        }
        setParameterJson(jsonStr);
        System.out.println(jsonStr);
        if(path.contains("/common"))
            return true;
        if (AuthUser.isLogin())
            return true;
        simpleRender(response,new JsonResult(ApiCodeConstant.COMMON_ERROR_LOGIN_TIMEOUT,"尚未登录，禁止访问"));
        return false;
    }

    /**
     * 渲染输出流
     *
     * @param response {@link HttpServletResponse}
     * @param result      输出信息
     * @throws IOException see {@link javax.servlet.ServletResponse#getWriter()}
     */
    private void simpleRender(HttpServletResponse response, JsonResult result){
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("expires", "0");
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.print(JSONObject.fromObject(result).toString());
        printWriter.flush();
    }


    //在业务处理器处理请求执行完成后,生成视图之前执行的动作
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        // System.out.println("==============执行顺序: 2、postHandle================");
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * <p/>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        // TODO Auto-generated method stub
        //    System.out.println("==============执行顺序: 3、afterCompletion================");
    }


}
