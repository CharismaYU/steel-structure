package com.site.blog.my.core.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yxn
 * @link
 */
@Controller
public class indexController {

    /**
     * 首页
     *
     * @return
     */
    @GetMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        return "user/login";
    }

}
