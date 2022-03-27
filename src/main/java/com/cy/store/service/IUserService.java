package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.util.PageRequest;
import com.cy.store.util.PageResult;

import java.util.Date;
import java.util.List;


/**
 * 用户注册接口
 * @author yk
 * @date 2022/3/13  11:11
 */
public interface IUserService {
    /**
     * 用户注册
     * @param user 用户数据
     */
    void reg(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User login(String username, String password);

    /**
     * 修改密码
     * @param uid 当前用户的uid
     * @param password xiugai
     * @param oldPassword
     * @param newPassword
     */
     void changePassword(Integer uid, String password, String oldPassword, String newPassword);

    /**
     * 查找所有用户
     * @return
     */
    List<User> findAll();

    /**
     * 分页查询接口
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult findPage(PageRequest pageRequest);

    /**
     * 根据uid 查询用户
     * @param uid
     * @return
     */
    User getByUid(Integer uid);

    /**
     * 更新用户资料
     * @param uid 用户的uid
     * @param userName 用户的 username
     * @param user 用户对象的其他数据
     */
    void changeInfo(Integer uid, String userName,User user);
    /**
     * 修改用户头像
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @param avatar 用户的新头像的路径
     */
    void changeAvatar(Integer uid, String username, String avatar);
}