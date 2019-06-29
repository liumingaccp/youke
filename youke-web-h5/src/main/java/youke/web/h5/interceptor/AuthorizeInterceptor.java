package youke.web.h5.interceptor;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.utils.AESUtil;
import youke.common.utils.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by liuming on 2017-12-13
 */
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(AuthorizeInterceptor.class);

    private static final ThreadLocal parameterJson = new ThreadLocal();

    public static void setParameterJson(String dataSourceType) {
        parameterJson.set(dataSourceType);
    }

    public static String getParameterJson() {
        return (String) parameterJson.get();
    }

    public void destroy() {}

    public void init() {}

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
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
        String path = request.getServletPath();
        System.out.println("----------------------------------");
        System.out.println(path+(request.getQueryString()==null?"":"?"+request.getQueryString()));
        System.out.println(jsonStr);
        return true;
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

    public static void main(String[] args) {
        boolean doReader=true,doAuth = true;
        System.out.println(doReader+"   "+doAuth);
    }

}
