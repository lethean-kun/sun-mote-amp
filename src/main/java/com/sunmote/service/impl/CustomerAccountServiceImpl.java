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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        // 查询客户name
        List<Long> allCustomerId = result.getRecords().stream().map(CustomerAccount::getCustomerId).collect(Collectors.toList());
        List<Customer> customers = customerDAO.selectBatchIds(allCustomerId);
        // 转换customers 为map，id为id，key为Customer
        Map<Long, String> idToCustomerName = customers.stream().collect(Collectors.toMap(Customer::getId, Customer::getCorpName));

        // 花费
        List<String> allAccountId = result.getRecords().stream().map(CustomerAccount::getAccountId).collect(Collectors.toList());
        QueryWrapper<AccountBill> abWrapper = new QueryWrapper<>();
        abWrapper.in("accountId", allAccountId);
        if (queryPageBean.getStart() != null) {
            abWrapper.ge("date", DateUtil.fmtData(queryPageBean.getStart()));
        }
        if (queryPageBean.getEnd() != null) {
            abWrapper.le("date", DateUtil.fmtData(queryPageBean.getEnd()));
        }
        Map<String, List<AccountBill>> abMap = accountBillDAO.selectList(abWrapper).stream().collect(Collectors.groupingBy(AccountBill::getAccountId));

        // 充值总额

        QueryWrapper<AccountRecharge> arWrapper = new QueryWrapper<>();
        arWrapper.in("accountId", allAccountId);
        Map<String, List<AccountRecharge>> arMap = accountRechargeDAO.selectList(arWrapper).stream().collect(Collectors.groupingBy(AccountRecharge::getAccountId));

        result.getRecords().forEach(
            customerAccount -> {
                if (customerAccount.getCustomerId() != null) {
                    customerAccount.setCustomerName(idToCustomerName.get(customerAccount.getCustomerId()));
                }

                // 时间内花费总额
                double adCost = 0;
                if (abMap.get(customerAccount.getAccountId()) != null) {
                    for (AccountBill ab : abMap.get(customerAccount.getAccountId())) {
                        adCost += ab.getAmount();
                    }
                }
                customerAccount.setAdCost(adCost);

                // 充值总额
                double accountRechargeAmount = 0;
                if (arMap.get(customerAccount.getAccountId()) != null) {
                    for (AccountRecharge accountRecharge : arMap.get(customerAccount.getAccountId())) {
                        accountRechargeAmount += accountRecharge.getRechargeAmount();
                    }
                }
                customerAccount.setAccountRechargeAmount(accountRechargeAmount);
            }
        );

        List<CustomerAccount> r = result.getRecords().stream().
            sorted(Comparator.comparing(CustomerAccount::getAdCost).reversed()).collect(Collectors.toList());
        return new PageResult<>(Long.valueOf(dao.selectCount(wrapper)), r);
    }
}
