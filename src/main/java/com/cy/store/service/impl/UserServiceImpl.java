package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import com.cy.store.util.PageRequest;
import com.cy.store.util.PageResult;
import com.cy.store.util.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sun.security.util.Password;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户注册接口实现类
 * @author yk
 * @date 2022/3/13  11:12
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        // 通过user参数来获取传递过来的参数
        String username = user.getUsername();
        // 调用findByUsername(username)来判断用户是否被注册过
        User result = userMapper.findByUsername(username);
        // 判断结果集是否为null，侧抛出用户名被占用的异常
        if (result != null) {
            // 抛出异常
            throw new UsernameDuplicateException("尝试注册的用户名[" + username + "]已经被占用");
        }
        //补全数据
        user.setIsDelete(0);
        Date date = new Date();
       //补全4个日志字段信息
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        user.setCreatedTime(date);
        user.setCreatedUser(user.getUsername());
        user.setModifiedTime(date);
        user.setModifiedUser(user.getUsername());

        // 执行注册过程
        Integer rows = userMapper.insert(user);
        if ( rows != 1) {
            //执行异常
            throw new InsertException("添加用户数据出现未知错误，请联系系统管理员");
        }
    }
    /**
     * 执行密码加密
     * @param password 原始密码
     * @param salt 盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    @Override
    public User login(String username, String password) {
        //根据用户名查找用户的数据是否存在，如果不再在则抛出异常
        User result = userMapper.findByUsername(username);
        if (result == null) {
            throw new UserNotFoundException("用户名不存在");
        }
        //检测密码是否匹配
        // 1.先获取该用户注册时候的盐值
        // 2.把前端给的明码密码进行md5三次加密(利用先密码加密的盐值）
        // 3.进行数据库加密的密码跟现在加密后的密码进行比较
        String salt = result.getSalt();
        String md5Password = getMd5Password(password,salt);
        //字符串用equals
        if (!result.getPassword().equals(md5Password)) {
            throw new PasswordNotMatchException("密码不正确，请重新输密码");
        }
        //判断is_delete 字段的值是否为1 表示被标记为删除
        if (result.getIsDelete() ==  1) {
            throw new UserNotFoundException("用户名不存在");
        }
        //最后返回用户信息,只需要三个数据，提高性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setPassword(result.getPassword());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String modifiedUser, String oldPassword, String newPassword) {
        // 查询uid的用户数据
        User user = userMapper.findByUid(uid);
        if (user == null) {
            //抛出异常
            throw new UserNotFoundException("用户数据不存在");
        }
        // 判断该用户数据是否被删除 is_Delete
        Integer integer = user.getIsDelete();
        if ( integer.equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 从用户数据获得盐值
        String salt = user.getSalt();
        // 将参数oldPassword结合盐值加密，得到oldMd5Password
        String oldMd5Password = getMd5Password(oldPassword, salt);
        // 判断查询结果中的password与oldMd5Password是否不一致
        if (!user.getPassword().contentEquals(oldMd5Password)) {
            // 是：抛出PasswordNotMatchException异常
            throw new PasswordNotMatchException("原密码错误");
        }
        // 将参数newPassword结合盐值加密，得到newMd5Password
        String newMd5Password = getMd5Password(newPassword, salt);
        // 创建当前时间对象
        Date now = new Date();
        // 调用userMapper的updatePasswordByUid()更新密码，并获取返回值
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, modifiedUser, now);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    /**
     * 调用分页插件完成分页
     * @param pageQuery
     * @return
     */
    private PageInfo<User> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<User> sysMenus = userMapper.selectPage();
        return new PageInfo<User>(sysMenus);
    }

    /**
     * 根据uid 查询用户
     * @param uid
     * @return
     */
    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在");
        }
        //前端页面需要更新的数据
        User user = new User();
        user.setPhone(result.getPhone());
        user.setUsername(result.getUsername());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    /**
     * 更新用户资料 springboot 只会根据前端表单需要更新的值才会用到，手动再将uid/username
     * 封装到user对象中
     * @param uid 用户的uid
     * @param userName 用户的 username
     * @param user 用户对象的其他数据
     */
    @Override
    public void changeInfo(Integer uid, String userName, User user) {
        // 获得用户资料
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在");
        }
        // 修改用户资料
        user.setUid(uid);
        user.setModifiedUser(userName);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new UpdateException("更新用户数据产生未知错误");
        }
    }
    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 检查查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 检查查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建当前时间对象
        Date now = new Date();
        // 调用userMapper的updateAvatarByUid()方法执行更新，并获取返回值
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, now);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }
}