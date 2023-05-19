package com.sunmote.service;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.Customer;

public interface CustomerService {

    void create(Customer customer);

    void update(Long id, Customer customer);

    PageResult<Customer> readList(QueryPageBean queryPageBean);

}
