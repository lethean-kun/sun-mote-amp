package com.sunmote.controller;

import com.sunmote.auth.AuthnUserDetails;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.CustomerAccount;
import com.sunmote.domain.CustomerPayment;
import com.sunmote.service.CustomerAccountService;
import com.sunmote.service.CustomerPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * </p>
 */
@RestController
@RequestMapping("/customerPayment")
@Slf4j
public class CustomerPaymentController {

    final CustomerPaymentService customerPaymentService;

    public CustomerPaymentController(
            final CustomerPaymentService customerPaymentService
    ) {

        this.customerPaymentService = customerPaymentService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResult<CustomerPayment> readCustomerPayment(
            @RequestParam(required = false) Long customerId,
            QueryPageBean queryPageBean
    ) {
        return customerPaymentService.readList(customerId, queryPageBean);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void createCustomerPayment(CustomerPayment customer) {
        customerPaymentService.create(customer);
    }

    // 获取当前登录的用户
    private Long getUserId() {
        return ((AuthnUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
