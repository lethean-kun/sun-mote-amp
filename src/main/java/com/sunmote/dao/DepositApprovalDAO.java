package com.sunmote.dao;

import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.DepositApproval;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DepositApprovalDAO {

    @Insert("insert into DepositApproval(deposit,userId,remark) values('${depositApproval.deposit}', ${depositApproval.userId}, '${depositApproval.remark}')")
    void createUserDepositApproval(@Param("depositApproval") DepositApproval depositApproval);

    @Update("update DepositApproval set deposit = '${depositApproval.deposit}', status = ${depositApproval.status} where id =  ${id}")
    void updateUserDepositApproval(Long id, @Param("depositApproval") DepositApproval depositApproval);

    @Select({
        "<script>",
        "select sum(deposit) from DepositApproval where isDelete = 0 and status = 1",
        "<if test='userId != null'>",
        "   and userId = #{userId}",
        "</if>",
        "</script>"
    })
    Double readUserDeposit(@Param("userId") Long userId);

    @Select("select count(1) from DepositApproval where userId = ${userId}")
    Long readUserDepositApprovalCount(Long userId);

    @Select("select * from DepositApproval where userId = ${userId} order by id desc limit ${queryPageBean.limit} offset ${queryPageBean.offset}")
    List<DepositApproval> readUserDepositApprovalList(
        Long userId,
        @Param("queryPageBean") QueryPageBean queryPageBean
    );

    @Select({
        "<script>",
        "select count(1) from DepositApproval where isDelete = 0",
        "<if test='status != null'>",
        "   and status in ",
        "   <foreach collection='status' item='item' open='(' separator=',' close=')'>",
        "       #{item}",
        "   </foreach>",
        "</if>",
        "<if test='userId != null'>",
        "   and userId = #{userId}",
        "</if>",
        "</script>"
    })
    Long readDepositApprovalListByStatusCount(
        @Param("userId") final Long userId,
        @Param("status") final List<Long> status
    );

    @Select({
        "<script>",
        "select * from DepositApproval where isDelete = 0",
        "<if test='status != null'>",
        "   and status in ",
        "   <foreach collection='status' item='item' open='(' separator=',' close=')'>",
        "       #{item}",
        "   </foreach>",
        "</if>",
        "<if test='userId != null'>",
        "   and userId = #{userId}",
        "</if>",
        "order by id desc limit ${queryPageBean.limit} offset ${queryPageBean.offset}",
        "</script>"
    })
    List<DepositApproval> readDepositApprovalListByStatus(
        @Param("userId") final Long userId,
        @Param("status") List<Long> status,
        @Param("queryPageBean") QueryPageBean queryPageBean
    );

    @Select("select count(1) from DepositApproval")
    Long readAllDepositToApprovalCount();

    @Select("select * from DepositApproval order by id desc limit ${queryPageBean.limit} offset ${queryPageBean.offset}")
    List<DepositApproval> readAllDepositApprovalList(
        @Param("queryPageBean") QueryPageBean queryPageBean
    );

}
