package com.sunmote.controller;

import com.sunmote.service.DepositApprovalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * 安全控制器
 * </p>
 *
 * @author: JavaLover
 * @date: 2021/4/12 18:03
 */
@Controller
@Slf4j
public class SecurityController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping()
    public String index(Model model) {
        return "userIndex";
    }

    @RequestMapping("/users")
    public String home(Model model) {
        return "userIndex";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        return "adminIndex";
    }

    @RequestMapping("/accessDenied")
    public String accessDenied(Model model) {
        return "403";
    }
}
