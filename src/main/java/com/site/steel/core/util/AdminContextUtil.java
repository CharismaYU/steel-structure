package com.site.steel.core.util;

import com.site.steel.core.entity.AdminUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : yxn
 * @Date ： 2020-04-12 10:08
 * @Description : 用户上下文工具
 * @Version :  0.1$
 */
public class AdminContextUtil {
    public static final String REQUEST_ATTRIBUTE_CURRENT_USER = AdminUser.class.getName();
    public static final String REQUEST_ATTRIBUTE_CURRENT_USER_ID = "currentUserId";

    /**
     * 获取当前AppUser
     *
     * @param request
     * @return
     */
    public static AdminUser getCurrentUser(HttpServletRequest request) {
        if (request != null) {
            Object user = request.getAttribute(REQUEST_ATTRIBUTE_CURRENT_USER);
            if (user != null && user instanceof AdminUser) {
                return (AdminUser) user;
            }
        }
        return null;
    }

    public static void setCurrentUser(HttpServletRequest request, AdminUser user) {
        if (request == null) {
            return;
        }
        if (user != null) {
            request.setAttribute(REQUEST_ATTRIBUTE_CURRENT_USER, user);
            request.setAttribute(REQUEST_ATTRIBUTE_CURRENT_USER_ID, user.getAdminUserId());
        } else {
            request.removeAttribute(REQUEST_ATTRIBUTE_CURRENT_USER);
        }
    }

    /**
     * 获取当前登陆用户的ID
     *
     * @return
     */
    public static Integer getCurrentUserId(HttpServletRequest request) {
        if (request != null) {
            Integer userId = (Integer) request.getAttribute(REQUEST_ATTRIBUTE_CURRENT_USER_ID);
            if (userId != null) {
                return userId;
            }
        }
        AdminUser curUser = getCurrentUser(request);
        return curUser != null ? curUser.getAdminUserId() : null;
    }
}
