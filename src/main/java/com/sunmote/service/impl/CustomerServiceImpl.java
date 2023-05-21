package com.sunmote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.AccountBillDAO;
import com.sunmote.dao.CustomerAccountDAO;
import com.sunmote.dao.CustomerDAO;
import com.sunmote.dao.CustomerPaymentDAO;
import com.sunmote.domain.AccountBill;
import com.sunmote.domain.Customer;
import com.sunmote.domain.CustomerAccount;
import com.sunmote.domain.CustomerPayment;
import com.sunmote.service.CustomerService;
import com.sunmote.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    final CustomerDAO dao;

    final CustomerAccountDAO customerAccountDAO;

    final AccountBillDAO accountBillDAO;

    final CustomerPaymentDAO customerPaymentDAO;


    public CustomerServiceImpl(CustomerDAO dao, CustomerAccountDAO customerAccountDAO, AccountBillDAO accountBillDAO, CustomerPaymentDAO customerPaymentDAO) {
        this.dao = dao;
        this.customerAccountDAO = customerAccountDAO;
        this.accountBillDAO = accountBillDAO;
        this.customerPaymentDAO = customerPaymentDAO;
    }


    @Override
    public void create(final Customer customer) {
        dao.insert(customer);
    }

    @Override
    public void update(final Long id, final Customer customer) {
        customer.setId(id);
        dao.updateById(customer);
    }

    @Override
    public PageResult<Customer> readList(String keywords, final QueryPageBean queryPageBean) {

        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        if (keywords != null) {
            wrapper.like("corpName", keywords);
        }

        Page<Customer> result = dao.selectPage(new Page<>(queryPageBean.getPage(), queryPageBean.getLimit()), wrapper);
        result.getRecords().forEach(customer -> {
            // 计算广告花费
            QueryWrapper<CustomerAccount> caWrapper = new QueryWrapper<>();
            caWrapper.eq("customerId", customer.getId());
            List<CustomerAccount> accountList = customerAccountDAO.selectList(caWrapper);
            List<String> accountIdList = accountList.stream().map(CustomerAccount::getAccountId).collect(Collectors.toList());

            if (accountIdList.size() < 1) {
                return;
            }

            QueryWrapper<AccountBill> abWrapper = new QueryWrapper<>();
            abWrapper.in("accountId", accountIdList);
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
            customer.setAdCost(adCost);
            // 计算账单余额 = 付款总额 - 所有账号花费总额
            QueryWrapper<CustomerPayment> cpWrapper = new QueryWrapper<>();
            cpWrapper.eq("customerId", customer.getId());
            List<CustomerPayment> customerPayments = customerPaymentDAO.selectList(cpWrapper);
            double customerBalance = 0;
            for (CustomerPayment customerPayment : customerPayments) {
                customerBalance += customerPayment.getAmount();
            }
            for (CustomerAccount customerAccount : accountList) {
                customerBalance -= customerAccount.getCostAmount();
            }
            customer.setCustomerBalance(customerBalance);

        });


        List<Customer> r = result.getRecords().stream().sorted(Comparator.comparing(Customer::getAdCost).reversed()).collect(Collectors.toList());

        return new PageResult<>(Long.valueOf(dao.selectCount(wrapper)), r);
    }
}
