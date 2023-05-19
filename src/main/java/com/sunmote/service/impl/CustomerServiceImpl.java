package com.sunmote.service.impl;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.CustomerDAO;
import com.sunmote.domain.Customer;
import com.sunmote.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerDAO dao;

    @Override
    public void create(final Customer customer) {
        dao.insert(customer);
    }

    @Override
    public void update(final Long id, final Customer customer) {
        customer.setId(id);
        dao.updateById(customer);
    }

    @Override
    public PageResult<Customer> readList(final QueryPageBean queryPageBean) {
        return new PageResult<>(
            dao.readCount(),
            dao.readList(queryPageBean)
        );
    }
}
