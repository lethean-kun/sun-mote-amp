package com.sunmote.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.Customer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CustomerDAO extends BaseMapper<Customer> {

    @Select("select count(1) from Customer")
    Long readCount();

    @Select("select * from Customer order by createdAt desc limit ${queryPageBean.limit} offset ${queryPageBean.offset}")
    List<Customer> readList(@Param("queryPageBean") QueryPageBean queryPageBean);

}
