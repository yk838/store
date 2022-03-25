package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


/**
 * @author yk
 * @date 2022/3/13  11:30
 */
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    /**
     * 注册测试
     */
    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("test001");
            user.setPassword("123");
            user.setGender(1);
            user.setPhone("17858802974");
            user.setEmail("lower@cy.cn");
            userService.reg(user);
            System.out.println("注册成功！");
        } catch (ServiceException e) {
            //获取类对象，再获取类的名称
            System.out.println("注册失败！" + e.getClass().getSimpleName());
            //获取异常信息
            System.out.println(e.getMessage());
        }
    }

    /**
     * 测试登录
     */
    @Test
    public void login() {
        try {
            User user = userService.login("yk5", "123");
            System.out.println(user);
            System.out.println("登陆成功");
        } catch (ServiceException e) {
            System.out.println("登陆失败！"+ e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void changePassword() {
        try {
            Integer uid = 8;
            String username = "test001";
            String oldPassword = "123";
            String newPassword = "888888";
            userService.changePassword(uid, username, oldPassword, newPassword);
            System.out.println("密码修改成功！");
        } catch (ServiceException e) {
            System.out.println("密码修改失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void findAll() {
        List<User> users = userService.findAll();
        System.out.println(users);
    }
    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(7));
    }
    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("12345678998");
        user.setEmail("@111111111qq.com");
        user.setGender(0);
        userService.changeInfo(9,"yk6",user);
    }
}