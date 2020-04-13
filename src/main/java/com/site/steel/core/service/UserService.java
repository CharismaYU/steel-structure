package com.site.steel.core.service;

import com.site.steel.core.controller.vo.UserVO;
import com.site.steel.core.entity.User;

import java.util.List;

/**
 * @author yuxuenan
 */
public interface UserService {

    User login(String userName, String password);

    /**
     * 获取用户信息
     *
     * @param loginUserId
     * @return
     */
    User getUserDetailById(Integer loginUserId);

    /**
     * 修改当前登录用户的密码
     *
     * @param loginUserId
     * @param originalPassword
     * @param newPassword
     * @return
     */
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    /**
     * 修改当前登录用户的名称信息
     *
     * @param loginUserId
     * @param loginUserName
     * @param nickName
     * @return
     */
    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);

    List<User> findAll();

    boolean insert(UserVO userVO);
}
