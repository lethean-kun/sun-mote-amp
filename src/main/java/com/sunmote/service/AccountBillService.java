package com.sunmote.service;


import com.sunmote.domain.AccountBill;

public interface AccountBillService {

    void create(AccountBill accountBill);

    void update(Long id, AccountBill accountBill);

    void upsert(String date, AccountBill accountBill);

}
