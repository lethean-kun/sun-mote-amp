package com.sunmote.service.impl;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.AccountRechargeApprovalDAO;
import com.sunmote.domain.AccountRechargeApproval;
import com.sunmote.domain.User;
import com.sunmote.service.AccountRechargeApprovalService;
import com.sunmote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountRechargeApprovalServiceImpl implements AccountRechargeApprovalService {

    @Autowired
    AccountRechargeApprovalDAO accountRechargeApprovalDAO;

    @Autowired
    UserService userService;

    @Override
    public Double readUserAccountRecharge(final Long userId, final String accountId) {
        return accountRechargeApprovalDAO.readUserAccountRecharge(userId, accountId);
    }

    @Override
    public void createUserAccountRechargeApproval(final AccountRechargeApproval accountRechargeApproval) {

        User user = userService.getUserById(accountRechargeApproval.getUserId());

        if (accountRechargeApproval.getRechargeAmount() == null) {
            accountRechargeApproval.setRechargeAmount(0.);
        }
        if (accountRechargeApproval.getExchangeRate() == null) {
            accountRechargeApproval.setExchangeRate(0.);
        }
        if (accountRechargeApproval.getTargetCurrencyAmount() == null) {
            accountRechargeApproval.setTargetCurrencyAmount(0.);
        }

        accountRechargeApproval.setTargetCurrency("NGN");
//        accountRechargeApproval.setPayAmount(accountRechargeApproval.getRechargeAmount() * (1 - user.getRebate()));

        accountRechargeApprovalDAO.createUserAccountRechargeApproval(accountRechargeApproval);
    }

    @Override
    public void updateUserAccountRechargeApproval(
        final Long id,
        final AccountRechargeApproval accountRechargeApproval
    ) {

        AccountRechargeApproval old = accountRechargeApprovalDAO.getById(id);
        if (accountRechargeApproval.getExchangeRate() != null) {

            accountRechargeApproval.setTargetCurrencyAmount(
                old.getRechargeAmount() * accountRechargeApproval.getExchangeRate()
            );
        } else {
            accountRechargeApproval.setExchangeRate(0.);
            accountRechargeApproval.setTargetCurrencyAmount(0.);
        }

        accountRechargeApprovalDAO.updateUserAccountRechargeApproval(id, accountRechargeApproval);
    }

    @Override
    public PageResult<AccountRechargeApproval> readUserAccountRechargeApprovalList(
        final Long userId, final String accountId, final List<Long> status, final QueryPageBean queryPageBean
    ) {
        PageResult<AccountRechargeApproval> result;

        result = new PageResult<>(
            accountRechargeApprovalDAO.readAccountRechargeApprovalListByStatusCount(userId, accountId, status),
            accountRechargeApprovalDAO.readAccountRechargeApprovalListByStatus(userId, accountId, status, queryPageBean)
        );

        result.getRows().forEach(
            r -> r.setUsername(userService.getUserById(r.getUserId()).getUsername())
        );

        return result;
    }
}