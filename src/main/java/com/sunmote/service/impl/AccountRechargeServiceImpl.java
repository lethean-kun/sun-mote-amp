package com.sunmote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.AccountRechargeDAO;
import com.sunmote.dao.CustomerPaymentDAO;
import com.sunmote.domain.AccountRecharge;
import com.sunmote.domain.CustomerPayment;
import com.sunmote.service.AccountRechargeService;
import com.sunmote.service.CustomerPaymentService;
import com.sunmote.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountRechargeServiceImpl implements AccountRechargeService {

    final AccountRechargeDAO dao;

    public AccountRechargeServiceImpl(AccountRechargeDAO dao) {
        this.dao = dao;
    }

    @Override
    public void create(final AccountRecharge accountRecharge) {
        dao.insert(accountRecharge);
    }

    @Override
    public void update(final Long id, final AccountRecharge accountRecharge) {
        accountRecharge.setId(id);
        dao.updateById(accountRecharge);
    }

    @Override
    public PageResult<AccountRecharge> readList(Long accountId, final QueryPageBean queryPageBean) {

        QueryWrapper<AccountRecharge> wrapper = new QueryWrapper<>();
        if (accountId != null) {
            wrapper.eq("accountId", accountId);
        }
        // TODO 有bug
        if (queryPageBean.getStart() != null) {
            wrapper.ge("createdAt", DateUtil.fmtData(queryPageBean.getStart()));
        }
        if (queryPageBean.getEnd() != null) {
            wrapper.le("createdAt", DateUtil.fmtData(queryPageBean.getEnd()));
        }

        Page<AccountRecharge> result = dao.selectPage(new Page<>(queryPageBean.getPage(), queryPageBean.getLimit()), wrapper);
        return new PageResult<>(Long.valueOf(dao.selectCount(wrapper)), result.getRecords());
    }
}
