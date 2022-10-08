package com.sunmote.service;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.CreateAccountApproval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CreateAccountApprovalService {

    Long readCreateAccountApprovalListByStatusCount(final Long userId, final List<Long> status);

    /**
     * 用户 发起开户申请
     */
    void createUserCreateAccountApproval(CreateAccountApproval createAccountApproval);

    /**
     * 管理员 审批 用户开户申请
     */
    void updateUserCreateAccountApproval(Long id, CreateAccountApproval createAccountApproval);

    /**
     * 获取用户开户申请列表
     */
    PageResult<CreateAccountApproval> readUserCreateAccountApprovalList(
        Long userId,
        final QueryPageBean queryPageBean
    );

    /**
     * 管理员获取用户开户申请列表
     */
    PageResult<CreateAccountApproval> readAllDepositToApprovalList(
        final Long userId,
        final List<Long> status,
        @Param("queryPageBean") QueryPageBean queryPageBean
    );

}
