package com.sunmote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.CustomerPaymentDAO;
import com.sunmote.domain.CustomerPayment;
import com.sunmote.service.CustomerPaymentService;
import com.sunmote.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPaymentServiceImpl implements CustomerPaymentService {
    @Autowired
    CustomerPaymentDAO dao;

    @Override
    public void create(final CustomerPayment customerPayment) {
        dao.insert(customerPayment);
    }

    @Override
    public void update(final Long id, final CustomerPayment customerPayment) {
        customerPayment.setId(id);
        dao.updateById(customerPayment);
    }

    @Override
    public PageResult<CustomerPayment> readList(Long customerId, final QueryPageBean queryPageBean) {

        QueryWrapper<CustomerPayment> wrapper = new QueryWrapper<>();
        if (customerId != null) {
            wrapper.eq("customerId", customerId);
        }

        if (queryPageBean.getStart() != null) {
            wrapper.ge("createdAt", DateUtil.fmtData(queryPageBean.getStart()));
        }
        if (queryPageBean.getEnd() != null) {
            wrapper.le("createdAt", DateUtil.fmtData(queryPageBean.getEnd()));
        }

        Page<CustomerPayment> result = dao.selectPage(new Page<>(queryPageBean.getPage(), queryPageBean.getLimit()), wrapper);
        return new PageResult<>(Long.valueOf(dao.selectCount(wrapper)), result.getRecords());
    }
}
