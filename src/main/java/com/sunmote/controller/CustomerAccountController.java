package com.sunmote.controller;

import com.sunmote.auth.AuthnUserDetails;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.CustomerAccount;
import com.sunmote.service.CustomerAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * </p>
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
    public PageResult<CustomerAccount> readUser(
        @RequestParam(required = false) Long customerId,
        QueryPageBean queryPageBean
    ) {
        return customerAccountService.readList(customerId, queryPageBean);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void createCustomerAccount(CustomerAccount customer) {
        customerAccountService.upsert(customer.getAccountId(), customer);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateCustomerAccount(
        @PathVariable(value = "id") Long id,
        CustomerAccount customer
    ) {
        customerAccountService.update(id, customer);
    }

    // 获取当前登录的用户
    private Long getUserId() {
        return ((AuthnUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
