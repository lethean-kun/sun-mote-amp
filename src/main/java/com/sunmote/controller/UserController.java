package com.sunmote.controller;

import com.sunmote.auth.AuthnUserDetails;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.User;
import com.sunmote.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    final UserService userService;

    public UserController(

        final UserService userService
    ) {

        this.userService = userService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResult<User> readUser(QueryPageBean queryPageBean) {
        return userService.readUserList(queryPageBean);
    }

    // 获取当前登录的用户
    private Long getUserId() {
        return ((AuthnUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
