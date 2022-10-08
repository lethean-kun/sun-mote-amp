package com.sunmote.service.impl;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.DepositApprovalDAO;
import com.sunmote.domain.DepositApproval;
import com.sunmote.service.AccountRechargeApprovalService;
import com.sunmote.service.DepositApprovalService;
import com.sunmote.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositApprovalServiceImpl implements DepositApprovalService {

    final AccountRechargeApprovalService accountRechargeApprovalService;
    final DepositApprovalDAO depositApprovalDAO;
    final UserService userService;

    public DepositApprovalServiceImpl(
        final AccountRechargeApprovalService accountRechargeApprovalService,
        final DepositApprovalDAO depositApprovalDAO,
        final UserService userService
    ) {
        this.accountRechargeApprovalService = accountRechargeApprovalService;
        this.depositApprovalDAO = depositApprovalDAO;
        this.userService = userService;
    }

    @Override
    public void createUserDepositApproval(final DepositApproval depositApproval) {
        depositApprovalDAO.createUserDepositApproval(depositApproval);
    }

    @Override
    public void updateUserDepositApproval(final Long id, final DepositApproval depositApproval) {
        depositApprovalDAO.updateUserDepositApproval(id, depositApproval);
    }

    @Override
    public Double readUserDeposit(final Long userId) {
        return depositApprovalDAO.readUserDeposit(userId);
    }

    @Override
    public Double readUserOverage(final Long userId) {
        Double deposit = depositApprovalDAO.readUserDeposit(userId);
        Double recharge = accountRechargeApprovalService.readUserAccountRecharge(userId, null);
        if (deposit != null) {
            if (recharge != null) {
                return deposit - recharge;
            } else {
                return deposit;
            }
        }
        return 0.;
    }

    @Override
    public PageResult<DepositApproval> readUserDepositApprovalList(
        final Long userId,
        final QueryPageBean queryPageBean
    ) {
        return new PageResult<>(
            depositApprovalDAO.readUserDepositApprovalCount(userId),
            depositApprovalDAO.readUserDepositApprovalList(userId, queryPageBean)
        );
    }

    @Override
    public PageResult<DepositApproval> readAllDepositToApprovalList(
        final Long userId,
        final List<Long> status,
        final QueryPageBean queryPageBean
    ) {
        PageResult<DepositApproval> result;

        result = new PageResult<>(
            depositApprovalDAO.readDepositApprovalListByStatusCount(userId, status),
            depositApprovalDAO.readDepositApprovalListByStatus(userId, status, queryPageBean)
        );

        result.getRows().forEach(
            r -> r.setUsername(userService.getUserById(r.getUserId()).getUsername())

        );
        return result;

    }
}
