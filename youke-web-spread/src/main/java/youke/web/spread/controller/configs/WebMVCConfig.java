package youke.web.spread.controller.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import youke.web.spread.controller.interceptor.AuthorizeInterceptor;

@SpringBootConfiguration
public class WebMVCConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthorizeInterceptor authorizeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizeInterceptor).addPathPatterns("/**");
    }



}
