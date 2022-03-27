package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @author yk
 * @date 2022/3/10  23:09
 */
//1用户注册，先判断是否数据库中已有该用户，然后在进行注册
public interface UserMapper {
    /**
     * 插入用户数据
     * @param user 用户的数据
     * @return 受影响的行数（增、删、该，都受影响的行数作为影响值，可以根据返回值来判断是否执行成功）
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户数据
     * @param username 用户名
     * @return 如果找到对应的用户则返回该用户的数据，如果没找到则返回null
     */
    User findByUsername(String username);

    /**
     * 根据uid更新用户的密码
     * @param uid 用户的id
     * @param password 新密码
     * @param modifiedUser 最后修改执行人
     * @param modifiedTime 最后修改时间
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(@Param("uid") Integer uid,
                                @Param("password") String password,
                                @Param("modifiedUser") String modifiedUser,
                                @Param("modifiedTime") Date modifiedTime);
    /**
     * 根据用户id查询用户数据
     * @param uid 用户id
     * @return 匹配的用户数据，如果没有匹配的用户数据，则返回null
     */
    User findByUid(Integer uid);
    /**
     * 查询全部用户
     * @return
     */
    List<User> selectAll();

    /**
     * 分页查询用户
     * @return
     */
    List<User> selectPage();

    /**
     * 根据uid更改用户数据
     * @param user
     * @return
     */
    Integer updateInfoByUid(User user);
    /**
     * 根据uid更新用户的头像
     * @param uid 用户的id
     * @param avatar 新头像的路径
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateAvatarByUid(
            @Param("uid") Integer uid,
            @Param("avatar") String avatar,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);
}