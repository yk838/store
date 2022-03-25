package com.cy.store.controller;

/**
 * @author yk
 * @date 2022/3/14  19:22
 */

import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制层的基类
 */
public class BaseController {
    public static final int ok = 200;
    // 请求处理方法，这个方法的返回值就是需要传递给前端的数据
    // 自动将异常对象传递此方法的参数列表上
    // 当前项目中产生的异常，统一拦截在方法上，这个方法此时就冲当的是请求处理方法，方法的返回值直接给前端
    @ExceptionHandler(ServiceException.class) //用于统一抛出异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if ( e instanceof UsernameDuplicateException) {
            result.setState(4000);
            result.setMessage("用户名被注册过了，请重新注册");
        } else if ( e instanceof UserNotFoundException) {
            result.setState(5001);
            result.setMessage("用户数据不存在的异常");
        } else if ( e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("用户密码错误的异常");
        }else if ( e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("用户注册过程中产生的位置异常");
        }
        return  result;
    }

    /**
     * 获取session对象的中uid
     * @param session session对象
     * @return 当前用户成功登录的uid的值
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取session 对象中的username
     * @param session
     * @return
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}