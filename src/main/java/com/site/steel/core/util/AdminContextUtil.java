package com.site.steel.core.util;

import com.site.steel.core.entity.AdminUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author : yxn
 * @Date ： 2020-04-12 10:08
 * @Description : 用户上下文工具
 * @Version :  0.1$
 */
public class AdminContextUtil {
    public static final String REQUEST_ATTRIBUTE_CURRENT_USER = AdminUser.class.getName();
    public static final String REQUEST_ATTRIBUTE_CURRENT_USER_ID = "currentAdminUserId";

    /**
     * 获取当前AppUser
     *
     * @param session
     * @return
     */
    public static AdminUser getCurrentUser(HttpSession session) {
        if (session != null) {
            Object user = session.getAttribute(REQUEST_ATTRIBUTE_CURRENT_USER);
            if (user != null && user instanceof AdminUser) {
                return (AdminUser) user;
            }
        }
        return null;
    }

    public static void setCurrentUser(HttpSession session, AdminUser user) {
        if (session == null) {
            return;
        }
        if (user != null) {
            session.setAttribute(REQUEST_ATTRIBUTE_CURRENT_USER, user);
            session.setAttribute(REQUEST_ATTRIBUTE_CURRENT_USER_ID, user.getAdminUserId());
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
        AdminUser curUser = getCurrentUser(session);
        return curUser != null ? curUser.getAdminUserId() : null;
    }
}
