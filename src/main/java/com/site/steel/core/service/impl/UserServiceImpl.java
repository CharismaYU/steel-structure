package com.site.steel.core.service.impl;

import com.site.steel.core.controller.vo.UserVO;
import com.site.steel.core.dao.UserMapper;
import com.site.steel.core.entity.User;
import com.site.steel.core.service.UserService;
import com.site.steel.core.util.MD5Util;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String userName, String password) {
        String passwordMd5 = MD5Util.MD5Encode(password, "UTF-8");
        return userMapper.login(userName, passwordMd5);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public boolean insert(UserVO userVO) {
        User user = new User();
        user.setLoginUserName(userVO.getLoginUserName());
        user.setPlaintextPassword(userVO.getLoginPassword());
        user.setLoginPassword(MD5Util.MD5Encode(userVO.getLoginPassword(), "UTF-8"));
        user.setNickName(userVO.getNickName());
        user.setPhone(userVO.getPhone());
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        int insert = userMapper.insert(user);
        if (insert > 0) {
            return true;
        }
        return false;
    }

    @Override
    public User getUserDetailById(Integer loginUserId) {
        return userMapper.selectByPrimaryKey(loginUserId);
    }

    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        User User = userMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (User != null) {
            String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
            String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            //比较原密码是否正确
            if (originalPasswordMd5.equals(User.getLoginPassword())) {
                //设置新密码并修改
                User.setLoginPassword(newPasswordMd5);
                User.setPlaintextPassword(newPassword);
                if (userMapper.updateByPrimaryKeySelective(User) > 0) {
                    //修改成功则返回true
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        User User = userMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (User != null) {
            //设置新密码并修改
            User.setLoginUserName(loginUserName);
            User.setNickName(nickName);
            if (userMapper.updateByPrimaryKeySelective(User) > 0) {
                //修改成功则返回true
                return true;
            }
        }
        return false;
    }
}
