package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理器拦截器的注册
 * @author yk
 * @date 2022/3/17  19:10
 */
//@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {
    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截器对象
        HandlerInterceptor interceptor= new LoginInterceptor();
        //添加白名单 放进一个list集合中
        List<String> a = new ArrayList<>();
        a.add("/bootstrap3/**");
        a.add("/css/**");
        a.add("/images/**");
        a.add("/js/**");
        a.add("/web/register.html");
        a.add("/web/login.html");
        a.add("/web/product.html");
        a.add("/users/login");
        a.add("/users/reg");
        //完成拦截器的注册
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(a);
    }
}