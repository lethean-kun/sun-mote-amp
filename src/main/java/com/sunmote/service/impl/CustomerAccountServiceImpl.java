package com.sunmote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.CustomerAccountDAO;
import com.sunmote.domain.CustomerAccount;
import com.sunmote.domain.CustomerPayment;
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
    public PageResult<CustomerAccount> readList(Long customerId, final QueryPageBean queryPageBean) {

        QueryWrapper<CustomerAccount> wrapper = new QueryWrapper<>();
        if (customerId != null) {
            wrapper.eq("customerId", customerId);
        }

        Page<CustomerAccount> result = dao.selectPage(new Page<>(queryPageBean.getPage(), queryPageBean.getLimit()), wrapper);
        return new PageResult<>(Long.valueOf(dao.selectCount(wrapper)), result.getRecords());
    }
}
