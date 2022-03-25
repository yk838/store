package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import com.cy.store.util.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author yk
 * @date 2022/3/14  18:59
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("/reg")
    public JsonResult<Object> reg (User user) {
        userService.reg(user);
        return new JsonResult<>(ok);
    }
    /*@GetMapping("/reg")
    public JsonResult<Object> reg (User user) {
        JsonResult<Object> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicateException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("用户注册时产生未知错误");
        }
        return result;
    }*/

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public JsonResult<User> login (String username,
                                   String password,
                                   HttpSession session) {

        User date = userService.login(username, password);
        session.setAttribute("uid",date.getUid());
        session.setAttribute("username",date.getUsername());
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(ok,date);
    }
    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        // 调用session.getAttribute("")获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        // 调用业务对象执行修改密码
        userService.changePassword(uid, username, oldPassword, newPassword);
        // 返回成功
        return new JsonResult<Void>(ok);
    }
    @GetMapping(value="/findAll")
    public Object findAll() {
        return userService.findAll();
    }

    @RequestMapping("/findPage")
    public Object findPage( PageRequest pageQuery) {
        return userService.findPage(pageQuery);
    }
    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        // 从HttpSession对象中获取uid
        Integer uid = getUidFromSession(session);
        // 调用业务对象执行获取数据
        User data = userService.getByUid(uid);
        // 响应成功和数据
        return new JsonResult<User>(ok, data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // 从HttpSession对象中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行修改用户资料
        userService.changeInfo(uid, username, user);
        // 响应成功
        return new JsonResult<Void>(ok);
    }
}