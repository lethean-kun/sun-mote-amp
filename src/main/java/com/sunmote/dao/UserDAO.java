package com.sunmote.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDAO extends BaseMapper<User> {

    @Select("select * from User where username = '${username}'")
    User getUserByUsername(String username);

    @Select("select * from User where id = ${id}")
    User getUserById(Long id);

    @Insert("insert into User(username,password,corpName,rebate) values('${user.username}', '${user.password}', '${user.corpName}', ${user.rebate})")
    void create(@Param("user") User user);

    @Update({
        "<script>",
        "update User",
        "<if test='user.rebate != null'>",
        "   set rebate = '${user.rebate}'",
        "</if>",
        "<if test='user.password != null and user.password !=\"\"'>",
        "   , password = '${user.password}'",
        "</if>",

        "where id =  ${id}",
        "</script>"
    })
    void update(Long id, @Param("user") User user);

    @Select("select count(1) from User")
    Long readUserCount();

    @Select("select * from User order by id desc limit ${queryPageBean.limit} offset ${queryPageBean.offset}")
    List<User> readUserList(@Param("queryPageBean") QueryPageBean queryPageBean);

}
