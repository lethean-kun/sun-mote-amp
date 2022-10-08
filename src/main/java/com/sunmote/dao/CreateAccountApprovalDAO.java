package com.sunmote.dao;

import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.CreateAccountApproval;
import com.sunmote.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CreateAccountApprovalDAO {

    @Select("select * from CreateAccountApproval where id = ${id}")
    CreateAccountApproval getById(Long id);

    @Insert("insert into CreateAccountApproval(timeZone,prodLink,rechargeAmount,userId) values('${createAccountApproval.timeZone}', '${createAccountApproval.prodLink}', ${createAccountApproval.rechargeAmount},${createAccountApproval.userId})")
    void createUserCreateAccountApproval(@Param("createAccountApproval") CreateAccountApproval createAccountApproval);

    @Update("update CreateAccountApproval set accountId = '${createAccountApproval.accountId}', status = ${createAccountApproval.status} where id =  ${id}")
    void updateUserCreateAccountApproval(
        Long id,
        @Param("createAccountApproval") CreateAccountApproval createAccountApproval
    );


    @Select("select count(1) from CreateAccountApproval where userId = ${userId}")
    Long readUserCreateAccountApprovalCount(Long userId);

    @Select("select * from CreateAccountApproval where userId = ${userId} order by id desc limit ${queryPageBean.limit} offset ${queryPageBean.offset}")
    List<CreateAccountApproval> readUserCreateAccountApprovalList(
        Long userId,
        @Param("queryPageBean") QueryPageBean queryPageBean
    );

    @Select({
        "<script>",
        "select count(1) from CreateAccountApproval where isDelete = 0",
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
    Long readCreateAccountApprovalListByStatusCount(
        @Param("userId") final Long userId,
        @Param("status") final List<Long> status
    );

    @Select({
        "<script>",
        "select * from CreateAccountApproval where isDelete = 0",
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
    List<CreateAccountApproval> readCreateAccountApprovalListByStatus(
        @Param("userId") final Long userId,
        @Param("status") List<Long> status,
        @Param("queryPageBean") QueryPageBean queryPageBean
    );

    @Select("select count(1) from CreateAccountApproval")
    Long readAllDepositToApprovalCount();

    @Select("select * from CreateAccountApproval order by id desc limit ${queryPageBean.limit} offset ${queryPageBean.offset}")
    List<CreateAccountApproval> readAllCreateAccountApprovalList(
        @Param("queryPageBean") QueryPageBean queryPageBean
    );

}
