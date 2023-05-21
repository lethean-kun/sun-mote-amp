package com.sunmote.service;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.AccountBill;
import com.sunmote.domain.Customer;

public interface AccountBillService {

    void create(AccountBill accountBill);

    void update(Long id, AccountBill accountBill);

    void upsert(String date, AccountBill accountBill);

}
