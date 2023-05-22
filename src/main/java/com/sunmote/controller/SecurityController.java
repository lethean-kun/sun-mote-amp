package com.sunmote.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * </p>
 *
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
