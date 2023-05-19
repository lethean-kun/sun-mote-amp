package com.sunmote.controller;

import com.sunmote.auth.AuthnUserDetails;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.CustomerAccount;
import com.sunmote.service.CustomerAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 安全控制器
 * </p>
 *
 * @author: JavaLover
 * @date: 2021/4/12 18:03
 */
@RestController
@RequestMapping("/customerAccount")
@Slf4j
public class CustomerAccountController {

    final CustomerAccountService customerAccountService;

    public CustomerAccountController(

        final CustomerAccountService customerAccountService
    ) {

        this.customerAccountService = customerAccountService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResult<CustomerAccount> readUser(QueryPageBean queryPageBean) {
        return customerAccountService.readList(queryPageBean);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void createCustomer(CustomerAccount customer) {
        customerAccountService.create(customer);
    }

    // 获取当前登录的用户
    private Long getUserId() {
        return ((AuthnUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
