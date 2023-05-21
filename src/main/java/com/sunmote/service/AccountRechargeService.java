package com.sunmote.service;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.AccountRecharge;
import com.sunmote.domain.CustomerPayment;

public interface AccountRechargeService {

    void create(AccountRecharge accountRecharge);

    void update(Long id, AccountRecharge accountRecharge);

    PageResult<AccountRecharge> readList(Long accountId, QueryPageBean queryPageBean);

}
