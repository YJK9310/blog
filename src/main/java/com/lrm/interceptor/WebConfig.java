package com.lrm.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by limi on 2017/10/15.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //设置已经定义的拦截器
        registry.addInterceptor(new LoginInterceptor())
                //拦截器拦截的路径
                .addPathPatterns("/admin/**")
                //拦截器排除的路径
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
