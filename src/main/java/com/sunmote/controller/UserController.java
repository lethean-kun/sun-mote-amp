package com.sunmote.controller;

import com.sunmote.auth.AuthnUserDetails;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.AccountRechargeApproval;
import com.sunmote.domain.CreateAccountApproval;
import com.sunmote.domain.DepositApproval;
import com.sunmote.domain.User;
import com.sunmote.service.AccountRechargeApprovalService;
import com.sunmote.service.DepositApprovalService;
import com.sunmote.service.UserService;
import com.sunmote.service.impl.CreateAccountApprovalServiceImpl;
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

    final DepositApprovalService depositApprovalService;
    final CreateAccountApprovalServiceImpl createAccountApprovalService;
    final AccountRechargeApprovalService accountRechargeApprovalService;
    final UserService userService;

    public UserController(
        final DepositApprovalService depositApprovalService,
        final CreateAccountApprovalServiceImpl createAccountApprovalService,
        final AccountRechargeApprovalService accountRechargeApprovalService,
        final UserService userService
    ) {
        this.depositApprovalService = depositApprovalService;
        this.createAccountApprovalService = createAccountApprovalService;
        this.accountRechargeApprovalService = accountRechargeApprovalService;
        this.userService = userService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResult<User> readUser(QueryPageBean queryPageBean) {
        return userService.readUserList(queryPageBean);
    }

    @PostMapping("/deposit_approval")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Description("付款申请")
    public void userDepositApproval(DepositApproval depositApproval) {
        depositApproval.setUserId(getUserId());
        depositApprovalService.createUserDepositApproval(depositApproval);
    }

    @GetMapping("/deposit_approval/list")
    @Description("付款申请列表")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public PageResult<DepositApproval> userDepositApprovalList(QueryPageBean queryPageBean) {
        return depositApprovalService.readUserDepositApprovalList(getUserId(), queryPageBean);
    }

    @GetMapping("/deposit_approval/deposit")
    @Description("付款总额")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Double userDeposit() {
        return depositApprovalService.readUserDeposit(getUserId());
    }

    @GetMapping("/deposit_approval/overage")
    @Description("付款余额")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Double userOverage() {
        return depositApprovalService.readUserOverage(getUserId());
    }

    @GetMapping("/account_recharge/amount")
    @Description("充值总额")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Double userAccountRechargeAmount(
        @RequestParam(value = "accountId", required = false) String accountId
    ) {
        return accountRechargeApprovalService.readUserAccountRecharge(getUserId(), accountId);
    }

    @GetMapping("/account/total")
    @Description("账户数目")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Long userAccountTotal() {
        ArrayList<Long> status = new ArrayList<>(1);
        status.add(1L);
        return createAccountApprovalService.readCreateAccountApprovalListByStatusCount(getUserId(), status);
    }

    @PostMapping("/account_approval")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Description("开户申请")
    public void userCreateAccountApproval(CreateAccountApproval createAccountApproval) {
        createAccountApproval.setUserId(getUserId());
        createAccountApprovalService.createUserCreateAccountApproval(createAccountApproval);
    }

    @GetMapping("/account_approval/list")
    @Description("开户申请列表")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public PageResult<CreateAccountApproval> userCreateAccountApprovalList(QueryPageBean queryPageBean) {
        return createAccountApprovalService.readUserCreateAccountApprovalList(getUserId(), queryPageBean);
    }

    @PostMapping("/account_recharge_approval/{accountId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Description("开户申请")
    public void userAccountRechargeApproval(
        @PathVariable(value = "accountId") String accountId,
        AccountRechargeApproval accountRechargeApproval
    ) {
        accountRechargeApproval.setUserId(getUserId());
        accountRechargeApproval.setAccountId(accountId);
        accountRechargeApproval.setStatus(0L);
        accountRechargeApprovalService.createUserAccountRechargeApproval(accountRechargeApproval);
    }

    @GetMapping("/account_recharge_approval/list")
    @Description("充值申请列表")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public PageResult<AccountRechargeApproval> userAccountRechargeApprovalList(
        @RequestParam(value = "status", required = false) List<Long> status,
        @RequestParam(value = "accountId") String accountId,
        QueryPageBean queryPageBean
    ) {
        return accountRechargeApprovalService.readUserAccountRechargeApprovalList(
            getUserId(),
            accountId,
            status,
            queryPageBean
        );
    }

    // 获取当前登录的用户
    private Long getUserId() {
        return ((AuthnUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
