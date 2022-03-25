package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 定义一个拦截器
 * @author yk
 * @date 2022/3/17  18:26
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 拦截前--在调用所有请求方法之前被自动调用执行的方法
     * 检测全局session对象中是否有对象uid数据，如果有有就放行，如果没有就重定向login。html
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器(url+ controlelr：映射)
     * @return true 放行   false 拦截 重定向
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // HttpServletRequest 对象来获取session对象
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if (uid == null) { //说明用户没有登陆过系统，则重定向login.html
            response.sendRedirect("/web/login.html");
            //结束后续调用
            return false;
        }
        //请求放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}