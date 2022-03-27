package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;


/**
 * 测试下user
 * @author yk
 * @date 2022/3/11  0:19
 */
//表示当前的类时一个测试类，不会随项目进行打包
@SpringBootTest
//表示启动这个单元测试类（单元测试类是不能运行的），需要传递一个参数，必须是SpringRunner的实例类型
//@RunWith(SpringRunner.class)
public class UserMapperTests {
    /**
     * 单元测试方法：
     * 1. 必须被@test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数不指定任何类型
     * 4，方法的访问修饰符必须是public
     */
    //报错原因： idea 有检测的功能，接口不能够直接创建bean的（动态代理来解决）
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    ///根据用户名查找用户信息
    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }
    //测试修改密码
    @Test
    public void updatePasswordByUid() {
        Integer uid = 5;
        String password = "321";
        String modifiedUser = "yk5";
        Date modifiedTime = new Date();
        Integer rows = userMapper.updatePasswordByUid(uid, password, modifiedUser, modifiedTime);
        System.out.println("rows"+"="+rows);
    }
    //判断修改的密码的用户在修改前有没有被注销掉
    @Test
    public void findByUid() {
        Integer uid = 5;
        User result = userMapper.findByUid(uid);
        System.out.println(result);
    }
    @Test
    public void selectPage() {
        List<User> users = userMapper.selectPage();
        System.out.println(users);
    }
    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(7);
        user.setPhone("12312312312");
        user.setEmail("1052018251@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }
    @Test
    public void updateAvatarByUid() {
        Integer uid = 9;
        String avatar = "/upload/avatar.png";
        String modifiedUser = "超级管理员";
        Date modifiedTime = new Date();
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, modifiedUser, modifiedTime);
        System.err.println("rows=" + rows);
    }
}