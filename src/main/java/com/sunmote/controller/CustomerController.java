package com.sunmote.controller;

import com.sunmote.auth.AuthnUserDetails;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.Customer;
import com.sunmote.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * </p>
 */
@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {

    final CustomerService customerService;

    public CustomerController(

            final CustomerService customerService
    ) {

        this.customerService = customerService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResult<Customer> readCustomer(
            @RequestParam(required = false) String keywords,
            QueryPageBean queryPageBean
    ) {
        return customerService.readList(keywords, queryPageBean);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void createCustomer(Customer customer) {
        customerService.create(customer);
    }

    // 获取当前登录的用户
    private Long getUserId() {
        return ((AuthnUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
