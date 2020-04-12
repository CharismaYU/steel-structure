package com.site.steel.core.controller.user;

import com.site.steel.core.controller.BaseApiController;
import com.site.steel.core.entity.User;
import com.site.steel.core.service.BlogService;
import com.site.steel.core.service.CategoryService;
import com.site.steel.core.service.CommentService;
import com.site.steel.core.service.LinkService;
import com.site.steel.core.service.TagService;
import com.site.steel.core.service.UserService;
import com.site.steel.core.util.UserContextUtil;
import com.site.steel.core.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * @author yxn
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseApiController {

    @Autowired
    private UserService userService;
    @Resource
    private BlogService blogService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private LinkService linkService;
    @Resource
    private TagService tagService;
    @Resource
    private CommentService commentService;


    @GetMapping({"/login"})
    public String login(HttpServletRequest request) {
        String language = request.getParameter("language");
        if (StringUtils.isEmpty(language)) {
            language = Locale.SIMPLIFIED_CHINESE.getLanguage();
        }
        request.getSession().setAttribute("language", language);
        return "user/login";
    }

    @GetMapping({"/register", "/register.html"})
    public String register() {
        return "user/register";
    }

    @PostMapping({"/register"})
    public String register(@RequestParam("userName") String userName,
                           @RequestParam("password") String password, HttpServletRequest request) {
        String language = request.getSession().getAttribute("language") + "";
        if (StringUtils.isEmpty(password)) {
            return MessageUtil.getMessage(language, "parametersIsEmpty");
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        //if (userService.updatePassword(loginUserId, originalPassword, newPassword)) {
        //修改成功后清空session中的数据，前端控制跳转至登录页
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        //  return "success";
        //} else {
        return "修改失败";
        // }
    }


    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        request.setAttribute("categoryCount", categoryService.getTotalCategories());
        request.setAttribute("blogCount", blogService.getTotalBlogs());
        request.setAttribute("linkCount", linkService.getTotalLinks());
        request.setAttribute("tagCount", tagService.getTotalTags());
        request.setAttribute("commentCount", commentService.getTotalComments());
        request.setAttribute("path", "index");
        return "user/index";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpServletRequest request) {
        // 国际化
        HttpSession session = request.getSession();
        String language = session.getAttribute("language") + "";
        Locale locale = MessageUtil.getLocale(language);
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", MessageUtil.getMessage(locale, "login.verifyCodeIsNull"));
            return "user/login";
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", MessageUtil.getMessage(locale, "login.userOrPasswordIsNull"));
            return "user/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", MessageUtil.getMessage(locale, "login.verifyCodeError"));
            return "user/login";
        }
        User user = userService.login(userName, password);
        if (user != null) {
            session.setAttribute("loginUser", user.getNickName());
            session.setAttribute("loginUserId", user.getUserId());
            UserContextUtil.setCurrentUser(request, user);
            //session过期时间设置为3600秒 即一小时
            session.setMaxInactiveInterval(60 * 60 * 1);
            return "redirect:/user/index";
        } else {
            session.setAttribute("errorMsg", MessageUtil.getMessage(locale, "login.loginError"));
            return "user/login";
        }
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        User User = userService.getUserDetailById(loginUserId);
        if (User == null) {
            return "user/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", User.getLoginUserName());
        request.setAttribute("nickName", User.getNickName());
        return "user/profile";
    }

    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        String language = request.getSession().getAttribute("language") + "";
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return MessageUtil.getMessage(language, "parametersIsEmpty");
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (userService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }

    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName) {
        if (StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (userService.updateName(loginUserId, loginUserName, nickName)) {
            return "success";
        } else {
            return "修改失败";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "user/login";
    }
}
