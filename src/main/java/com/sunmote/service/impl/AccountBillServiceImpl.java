package com.sunmote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunmote.dao.AccountBillDAO;
import com.sunmote.domain.AccountBill;
import com.sunmote.service.AccountBillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountBillServiceImpl implements AccountBillService {

    final AccountBillDAO accountBillDAO;

    public AccountBillServiceImpl(AccountBillDAO accountBillDAO) {
        this.accountBillDAO = accountBillDAO;
    }

    @Override
    public void create(AccountBill accountBill) {
        accountBillDAO.insert(accountBill);
    }

    @Override
    public void update(Long id, AccountBill accountBill) {
        accountBill.setId(id);
        accountBillDAO.updateById(accountBill);
    }

    @Override
    public void upsert(String date, AccountBill accountBill) {
        accountBill.setDate(date);

        QueryWrapper<AccountBill> caWrapper = new QueryWrapper<>();
        caWrapper.eq("date", date);
        caWrapper.eq("accountId", accountBill.getAccountId());
        List<AccountBill> accountBills = accountBillDAO.selectList(caWrapper);

        if (accountBills.size() < 1) {
            accountBillDAO.insert(accountBill);
        } else {
            accountBill.setId(accountBills.get(0).getId());
            accountBillDAO.updateById(accountBill);
        }
    }
}
