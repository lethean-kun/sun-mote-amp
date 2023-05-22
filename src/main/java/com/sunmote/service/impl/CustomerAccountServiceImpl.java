package com.sunmote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.AccountBillDAO;
import com.sunmote.dao.AccountRechargeDAO;
import com.sunmote.dao.CustomerAccountDAO;
import com.sunmote.dao.CustomerDAO;
import com.sunmote.domain.AccountBill;
import com.sunmote.domain.AccountRecharge;
import com.sunmote.domain.Customer;
import com.sunmote.domain.CustomerAccount;
import com.sunmote.service.CustomerAccountService;
import com.sunmote.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    final CustomerAccountDAO dao;

    final AccountBillDAO accountBillDAO;
    final AccountRechargeDAO accountRechargeDAO;
    final CustomerDAO customerDAO;

    public CustomerAccountServiceImpl(
        AccountBillDAO accountBillDAO, CustomerAccountDAO dao, AccountRechargeDAO accountRechargeDAO,
        final CustomerDAO customerDAO
    ) {
        this.accountBillDAO = accountBillDAO;
        this.dao = dao;
        this.accountRechargeDAO = accountRechargeDAO;
        this.customerDAO = customerDAO;
    }

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
    public void upsert(String accountId, CustomerAccount customerAccount) {

        QueryWrapper<CustomerAccount> caWrapper = new QueryWrapper<>();
        caWrapper.eq("accountId", accountId);
        List<CustomerAccount> customerAccountList = dao.selectList(caWrapper);
        if (customerAccountList.size() < 1) {
            dao.insert(customerAccount);
        } else {
            customerAccount.setId(customerAccountList.get(0).getId());
            dao.updateById(customerAccount);
        }
    }

    @Override
    public PageResult<CustomerAccount> readList(Long customerId, final QueryPageBean queryPageBean) {

        QueryWrapper<CustomerAccount> wrapper = new QueryWrapper<>();
        if (customerId != null) {
            wrapper.eq("customerId", customerId);
        }

        Page<CustomerAccount> result = dao.selectPage(
            new Page<>(queryPageBean.getPage(), queryPageBean.getLimit()),
            wrapper
        );
        result.getRecords().forEach(
            customerAccount -> {
                if (customerAccount.getCustomerId() != null) {
                    QueryWrapper<Customer> cWrapper = new QueryWrapper<>();
                    cWrapper.eq("id", customerAccount.getCustomerId());
                    Customer customer = customerDAO.selectOne(cWrapper);
                    customerAccount.setCustomerName(customer.getCorpName());
                }

                // 时间内花费总额
                QueryWrapper<AccountBill> abWrapper = new QueryWrapper<>();
                abWrapper.eq("accountId", customerAccount.getAccountId());
                if (queryPageBean.getStart() != null) {
                    abWrapper.ge("date", DateUtil.fmtData(queryPageBean.getStart()));
                }
                if (queryPageBean.getEnd() != null) {
                    abWrapper.le("date", DateUtil.fmtData(queryPageBean.getEnd()));
                }
                double adCost = 0;
                List<AccountBill> abList = accountBillDAO.selectList(abWrapper);
                for (AccountBill ab : abList) {
                    adCost += ab.getAmount();
                }
                customerAccount.setAdCost(adCost);

                // 充值总额
                QueryWrapper<AccountRecharge> arWrapper = new QueryWrapper<>();
                arWrapper.eq("accountId", customerAccount.getId());
                List<AccountRecharge> accountRecharges = accountRechargeDAO.selectList(arWrapper);
                double accountRechargeAmount = 0;
                for (AccountRecharge accountRecharge : accountRecharges) {
                    accountRechargeAmount += accountRecharge.getRechargeAmount();
                }
                customerAccount.setAccountRechargeAmount(accountRechargeAmount);

            }
        );

        List<CustomerAccount> r = result.getRecords().stream().
            sorted(Comparator.comparing(CustomerAccount::getAdCost).reversed()).collect(Collectors.toList());
        return new PageResult<>(Long.valueOf(dao.selectCount(wrapper)), r);
    }
}
