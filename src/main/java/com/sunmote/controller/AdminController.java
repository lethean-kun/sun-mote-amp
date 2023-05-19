package com.sunmote.controller;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.AccountRechargeApproval;
import com.sunmote.domain.CreateAccountApproval;
import com.sunmote.domain.DepositApproval;
import com.sunmote.domain.User;
import com.sunmote.service.CreateAccountApprovalService;
import com.sunmote.service.DepositApprovalService;
import com.sunmote.service.UserService;
import com.sunmote.service.impl.AccountRechargeApprovalServiceImpl;
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

    final AccountRechargeApprovalServiceImpl accountRechargeApprovalService;
    final CreateAccountApprovalService createAccountApprovalService;
    final DepositApprovalService depositApprovalService;
    final UserService userService;

    public AdminController(
        final AccountRechargeApprovalServiceImpl accountRechargeApprovalService,
        final CreateAccountApprovalService createAccountApprovalService,
        final DepositApprovalService depositApprovalService,
        final UserService userService
    ) {
        this.accountRechargeApprovalService = accountRechargeApprovalService;
        this.createAccountApprovalService = createAccountApprovalService;
        this.depositApprovalService = depositApprovalService;
        this.userService = userService;
    }

    @GetMapping("/user/deposit_approval/deposit")
    @Description("付款总额")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Double userDeposit(
        @RequestParam(required = false) Long userId
    ) {
        return depositApprovalService.readUserDeposit(userId);
    }


    @GetMapping("/user/deposit_approval/overage")
    @Description("付款余额")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Double userOverage(
        @RequestParam(required = false) Long userId
    ) {
        return depositApprovalService.readUserOverage(userId);
    }

    @GetMapping("/user/account_recharge/amount")
    @Description("充值总额")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Double userAccountRechargeAmount(
        @RequestParam(required = false) Long userId,
        @RequestParam(value = "accountId", required = false) String accountId
    ) {
        return accountRechargeApprovalService.readUserAccountRecharge(userId, accountId);
    }

    @GetMapping("/user/account/total")
    @Description("账户数目")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long userAccountTotal(
        @RequestParam(required = false) Long userId
    ) {
        ArrayList<Long> status = new ArrayList<>(1);
        status.add(1L);
        return createAccountApprovalService.readCreateAccountApprovalListByStatusCount(userId, status);
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


    @GetMapping("/user/deposit_approval")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Description("获取付款待审批列表")
    public PageResult<DepositApproval> getUserDepositToApprovalList(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) List<Long> status,
        QueryPageBean queryPageBean
    ) {
        return depositApprovalService.readAllDepositToApprovalList(userId, status, queryPageBean);
    }

    @PostMapping("/user/deposit_approval/{approvalId}/assent")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Description("同意用户付款申请")
    public void userDepositApprovalAssent(
        @PathVariable(value = "approvalId") Long approvalId,
        DepositApproval depositApproval
    ) {
        depositApproval.setStatus(1L);
        depositApprovalService.updateUserDepositApproval(approvalId, depositApproval);
    }

    @PostMapping("/user/deposit_approval/{approvalId}/refuse")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Description("拒绝用户付款申请")
    public void userDepositApprovalRefuse(
        @PathVariable(value = "approvalId") Long approvalId,
        DepositApproval depositApproval
    ) {
        depositApproval.setStatus(2L);
        depositApprovalService.updateUserDepositApproval(approvalId, depositApproval);
    }

    @GetMapping("/user/account_approval")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Description("获取开户待审批列表")
    public PageResult<CreateAccountApproval> getUserAccountToApprovalList(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) List<Long> status,
        QueryPageBean queryPageBean
    ) {
        return createAccountApprovalService.readAllDepositToApprovalList(userId, status, queryPageBean);
    }

    @PostMapping("/user/account_approval/{approvalId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Description("同意/拒绝 用户开户申请")
    public void userAccountApprovalHandle(
        @PathVariable(value = "approvalId") Long approvalId,
        @RequestParam(value = "status") Long status,
        CreateAccountApproval createAccountApproval
    ) {
        createAccountApproval.setStatus(status);
        createAccountApprovalService.updateUserCreateAccountApproval(approvalId, createAccountApproval);
    }

    @GetMapping("/user/account_recharge_approval")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Description("获取账户充值待审批列表")
    public PageResult<AccountRechargeApproval> getAccountRechargeToApprovalList(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) String accountId,
        @RequestParam(required = false) List<Long> status,
        QueryPageBean queryPageBean
    ) {
        return accountRechargeApprovalService.readUserAccountRechargeApprovalList(
            userId,
            accountId,
            status,
            queryPageBean
        );
    }

    @PostMapping("/user/account_recharge_approval/{approvalId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Description("同意/拒绝 用户账户充值申请")
    public void userAccountRechargeApprovalHandle(
        @PathVariable(value = "approvalId") Long approvalId,
        @RequestParam(value = "status") Long status,
        AccountRechargeApproval createAccountApproval
    ) {
        createAccountApproval.setStatus(status);
        accountRechargeApprovalService.updateUserAccountRechargeApproval(approvalId, createAccountApproval);
    }

}
