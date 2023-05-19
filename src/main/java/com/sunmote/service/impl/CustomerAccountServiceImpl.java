package com.sunmote.service.impl;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.CustomerAccountDAO;
import com.sunmote.domain.CustomerAccount;
import com.sunmote.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {
    @Autowired
    CustomerAccountDAO dao;

    @Override
    public void create(final CustomerAccount customer) {
        dao.insert(customer);
    }

    @Override
    public void update(final Long id, final CustomerAccount customer) {
        customer.setId(id);
        dao.updateById(customer);
    }

    @Override
    public PageResult<CustomerAccount> readList(final QueryPageBean queryPageBean) {
        return new PageResult<>(
            dao.readCount(),
            dao.readList(queryPageBean)
        );
    }
}
