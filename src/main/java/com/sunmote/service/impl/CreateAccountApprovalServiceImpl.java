package com.sunmote.service.impl;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.CreateAccountApprovalDAO;
import com.sunmote.domain.AccountRechargeApproval;
import com.sunmote.domain.CreateAccountApproval;
import com.sunmote.service.AccountRechargeApprovalService;
import com.sunmote.service.CreateAccountApprovalService;
import com.sunmote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateAccountApprovalServiceImpl implements CreateAccountApprovalService {

    @Autowired
    CreateAccountApprovalDAO createAccountApprovalDAO;
    @Autowired
    AccountRechargeApprovalService accountRechargeApprovalService;
    @Autowired
    UserService userService;

    @Override
    public Long readCreateAccountApprovalListByStatusCount(
        final Long userId, final List<Long> status
    ) {
        return createAccountApprovalDAO.readCreateAccountApprovalListByStatusCount(userId, status);
    }

    @Override
    public void createUserCreateAccountApproval(final CreateAccountApproval createAccountApproval) {
        if (createAccountApproval.getRechargeAmount() == null) {
            createAccountApproval.setRechargeAmount(0.);
        }

        createAccountApprovalDAO.createUserCreateAccountApproval(createAccountApproval);
    }

    @Override
    public void updateUserCreateAccountApproval(final Long id, final CreateAccountApproval createAccountApproval) {

        CreateAccountApproval old = createAccountApprovalDAO.getById(id);
        if (old.getRechargeAmount() != 0) {
            AccountRechargeApproval accountRechargeApproval = new AccountRechargeApproval();
            accountRechargeApproval.setAccountId(createAccountApproval.getAccountId());
            accountRechargeApproval.setUserId(old.getUserId());
            accountRechargeApproval.setRechargeAmount(old.getRechargeAmount());
            accountRechargeApproval.setExchangeRate(createAccountApproval.getExchangeRate());
            accountRechargeApproval.setTargetCurrencyAmount(
                old.getRechargeAmount() * createAccountApproval.getExchangeRate()
            );
            accountRechargeApproval.setStatus(1L);
            accountRechargeApprovalService.createUserAccountRechargeApproval(accountRechargeApproval);
        }

        createAccountApprovalDAO.updateUserCreateAccountApproval(id, createAccountApproval);
    }

    @Override
    public PageResult<CreateAccountApproval> readUserCreateAccountApprovalList(
        final Long userId, final QueryPageBean queryPageBean
    ) {
        return new PageResult<>(
            createAccountApprovalDAO.readUserCreateAccountApprovalCount(userId),
            createAccountApprovalDAO.readUserCreateAccountApprovalList(userId, queryPageBean)
        );
    }

    @Override
    public PageResult<CreateAccountApproval> readAllDepositToApprovalList(
        final Long userId, final List<Long> status, final QueryPageBean queryPageBean
    ) {
        PageResult<CreateAccountApproval> result;

        result = new PageResult<>(
            createAccountApprovalDAO.readCreateAccountApprovalListByStatusCount(userId, status),
            createAccountApprovalDAO.readCreateAccountApprovalListByStatus(userId, status, queryPageBean)
        );

        result.getRows().forEach(
            r -> r.setUsername(userService.getUserById(r.getUserId()).getUsername())

        );
        return result;
    }
}