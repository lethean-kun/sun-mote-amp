package com.sunmote.service;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.DepositApproval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepositApprovalService {

    /**
     * 用户 发起付款申请
     */
    void createUserDepositApproval(DepositApproval depositApproval);

    /**
     * 管理员 审批 用户付款申请
     */
    void updateUserDepositApproval(Long id, DepositApproval depositApproval);

    /**
     * 获取用户 付款 总额(已审批)
     */
    Double readUserDeposit(Long userId);

    /**
     * 获取用户 付款 余额
     */
    Double readUserOverage(Long userId);

    /**
     * 获取用户付款申请列表
     */
    PageResult<DepositApproval> readUserDepositApprovalList(
        Long userId,
        final QueryPageBean queryPageBean
    );

    /**
     * 管理员获取用户付款申请列表
     */
    PageResult<DepositApproval> readAllDepositToApprovalList(
        final Long userId,
        final List<Long> status,
        @Param("queryPageBean") QueryPageBean queryPageBean
    );

}
