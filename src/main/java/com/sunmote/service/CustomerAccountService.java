package com.sunmote.service;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.CustomerAccount;

public interface CustomerAccountService {

    void create(CustomerAccount customerAccount);

    void update(Long id, CustomerAccount customerAccount);

    PageResult<CustomerAccount> readList(QueryPageBean queryPageBean);

}
