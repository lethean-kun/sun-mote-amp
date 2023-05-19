package com.sunmote.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.CustomerAccount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomerAccountDAO extends BaseMapper<CustomerAccount> {

    @Select("select count(1) from CustomerAccount")
    Long readCount();

    @Select("select * from CustomerAccount order by createdAt desc limit ${queryPageBean.limit} offset ${queryPageBean.offset}")
    List<CustomerAccount> readList(@Param("queryPageBean") QueryPageBean queryPageBean);

}
