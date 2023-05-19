package com.sunmote.service;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.CustomerPayment;

public interface CustomerPaymentService {

    void create(CustomerPayment customerPayment);

    void update(Long id, CustomerPayment customerPayment);

    PageResult<CustomerPayment> readList(Long customerId, QueryPageBean queryPageBean);

}
