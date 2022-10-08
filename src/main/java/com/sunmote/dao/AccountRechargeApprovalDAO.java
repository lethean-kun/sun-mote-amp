package com.sunmote.dao;

import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.AccountRechargeApproval;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AccountRechargeApprovalDAO {

    @Select("select * from AccountRechargeApproval where id = ${id}")
    AccountRechargeApproval getById(Long id);

    @Insert(
        "insert into AccountRechargeApproval(accountId,rechargeAmount,payAmount,exchangeRate,targetCurrencyAmount,targetCurrency,status,userId) " +
            "values('${accountRechargeApproval.accountId}', ${accountRechargeApproval.rechargeAmount},${accountRechargeApproval.payAmount},${accountRechargeApproval.exchangeRate},${accountRechargeApproval.targetCurrencyAmount},'${accountRechargeApproval.targetCurrency}',${accountRechargeApproval.status},${accountRechargeApproval.userId})")
    void createUserAccountRechargeApproval(@Param("accountRechargeApproval") AccountRechargeApproval accountRechargeApproval);

    @Select({
        "<script>",
        "select sum(payAmount) from AccountRechargeApproval where isDelete = 0 and status = 1",
        "<if test='userId != null'>",
        "   and userId = ${userId}",
        "</if>",
        "<if test='accountId != null'>",
        "   and accountId = '${accountId}'",
        "</if>",
        "</script>"
    })
    Double readUserAccountRecharge(Long userId, String accountId);

    @Update("update AccountRechargeApproval set exchangeRate=${accountRechargeApproval.exchangeRate}, targetCurrencyAmount = ${accountRechargeApproval.targetCurrencyAmount}, status = ${accountRechargeApproval.status} where id =  ${id}")
    void updateUserAccountRechargeApproval(
        Long id,
        @Param("accountRechargeApproval") AccountRechargeApproval accountRechargeApproval
    );

    @Select({
        "<script>",
        "select count(1) from AccountRechargeApproval where isDelete = 0",
        "<if test='status != null'>",
        "   and status in ",
        "   <foreach collection='status' item='item' open='(' separator=',' close=')'>",
        "       #{item}",
        "   </foreach>",
        "</if>",
        "<if test='userId != null'>",
        "   and userId = ${userId}",
        "</if>",
        "<if test='accountId != null'>",
        "   and accountId = '${accountId}'",
        "</if>",
        "</script>"
    })
    Long readAccountRechargeApprovalListByStatusCount(
        final Long userId,
        final String accountId,
        @Param("status") final List<Long> status
    );

    @Select({
        "<script>",
        "select * from AccountRechargeApproval where isDelete = 0",
        "<if test='status != null'>",
        "   and status in ",
        "   <foreach collection='status' item='item' open='(' separator=',' close=')'>",
        "       #{item}",
        "   </foreach>",
        "</if>",
        "<if test='userId != null'>",
        "   and userId = ${userId}",
        "</if>",
        "<if test='accountId != null'>",
        "   and accountId = '${accountId}'",
        "</if>",
        "order by id desc limit ${queryPageBean.limit} offset ${queryPageBean.offset}",
        "</script>"
    })
    List<AccountRechargeApproval> readAccountRechargeApprovalListByStatus(
        final Long userId,
        final String accountId,
        @Param("status") List<Long> status,
        @Param("queryPageBean") QueryPageBean queryPageBean
    );

    @Select("select count(1) from AccountRechargeApproval")
    Long readAllDepositToApprovalCount();

    @Select("select * from AccountRechargeApproval order by id desc limit ${queryPageBean.limit} offset ${queryPageBean.offset}")
    List<AccountRechargeApproval> readAllAccountRechargeApprovalList(
        @Param("queryPageBean") QueryPageBean queryPageBean
    );

}
