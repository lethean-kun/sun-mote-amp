package com.sunmote.controller;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.User;
import com.sunmote.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    final UserService userService;

    public AdminController(
        final UserService userService
    ) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void createUser(User user) {
        userService.create(user);
    }

    @PostMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateUser(@PathVariable(value = "userId") Long userId, User user) {
        userService.update(userId, user);
    }

}
