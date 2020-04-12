package com.site.steel.core.util;

import com.site.steel.core.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author : yxn
 * @Date ： 2020-04-12 10:08
 * @Description : 用户上下文工具
 * @Version :  0.1$
 */
public class UserContextUtil {
    public static final String REQUEST_ATTRIBUTE_CURRENT_USER = User.class.getName();
    public static final String REQUEST_ATTRIBUTE_CURRENT_USER_ID = "currentUserId";

    /**
     * 获取当前AppUser
     *
     * @param session
     * @return
     */
    public static User getCurrentUser(HttpSession session) {
        if (session != null) {
            Object user = session.getAttribute(REQUEST_ATTRIBUTE_CURRENT_USER);
            if (user != null && user instanceof User) {
                return (User) user;
            }
        }
        return null;
    }

    public static void setCurrentUser(HttpSession session, User user) {
        if (session == null) {
            return;
        }
        if (user != null) {
            session.setAttribute(REQUEST_ATTRIBUTE_CURRENT_USER, user);
            session.setAttribute(REQUEST_ATTRIBUTE_CURRENT_USER_ID, user.getUserId());
        } else {
            session.removeAttribute(REQUEST_ATTRIBUTE_CURRENT_USER);
        }
    }

    /**
     * 获取当前登陆用户的ID
     *
     * @return
     */
    public static Integer getCurrentUserId(HttpSession session) {
        if (session != null) {
            Integer userId = (Integer) session.getAttribute(REQUEST_ATTRIBUTE_CURRENT_USER_ID);
            if (userId != null) {
                return userId;
            }
        }
        User curUser = getCurrentUser(session);
        return curUser != null ? curUser.getUserId() : null;
    }
}
