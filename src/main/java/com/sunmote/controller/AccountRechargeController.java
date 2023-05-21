package com.sunmote.controller;

import com.sunmote.auth.AuthnUserDetails;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.AccountRecharge;
import com.sunmote.domain.CustomerPayment;
import com.sunmote.service.AccountRechargeService;
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
@RequestMapping("/accountRecharge")
@Slf4j
public class AccountRechargeController {

    final AccountRechargeService accountRechargeService;

    public AccountRechargeController(
            final AccountRechargeService accountRechargeService
    ) {

        this.accountRechargeService = accountRechargeService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResult<AccountRecharge> readCustomerPayment(
            @RequestParam(required = false) Long accountId,
            QueryPageBean queryPageBean
    ) {
        return accountRechargeService.readList(accountId, queryPageBean);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void createCustomerPayment(AccountRecharge accountRecharge) {
        accountRechargeService.create(accountRecharge);
    }

    // 获取当前登录的用户
    private Long getUserId() {
        return ((AuthnUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
