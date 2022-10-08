package com.sunmote.service;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.AccountRechargeApproval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountRechargeApprovalService {

    /**
     * 获取用户 充值 总额(已审批)
     */
    Double readUserAccountRecharge(Long userId, final String accountId);


    /**
     * 用户 发起账户充值申请 status 为必填
     */
    void createUserAccountRechargeApproval(AccountRechargeApproval accountRechargeApproval);

    /**
     * 管理员 审批 用户账户充值申请
     */
    void updateUserAccountRechargeApproval(Long id, AccountRechargeApproval accountRechargeApproval);


    /**
     * 获取用户账户充值申请列表
     */
    PageResult<AccountRechargeApproval> readUserAccountRechargeApprovalList(
        final Long userId,
        final String accountId,
        final List<Long> status,
        @Param("queryPageBean") QueryPageBean queryPageBean
    );


}
