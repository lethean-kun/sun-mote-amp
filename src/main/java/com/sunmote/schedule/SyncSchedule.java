package com.sunmote.schedule;

import com.facebook.ads.sdk.*;
import com.sunmote.common.config.FacebookApiConfig;
import com.sunmote.domain.AccountBill;
import com.sunmote.domain.CustomerAccount;
import com.sunmote.service.AccountBillService;
import com.sunmote.service.CustomerAccountService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 描述:
 *
 * @author zongzekun
 * @create 2023-05-18 11:47
 */
@Component
public class SyncSchedule {

    final CustomerAccountService customerAccountService;
    final AccountBillService accountBillService;
    final FacebookApiConfig facebookApiConfig;

    public SyncSchedule(
        CustomerAccountService customerAccountService,
        AccountBillService accountBillService,
        FacebookApiConfig env
    ) {
        this.customerAccountService = customerAccountService;
        this.accountBillService = accountBillService;
        this.facebookApiConfig = env;
    }

    //    @Scheduled(fixedRate = 600000)
    public void syncAccountBill() {
        for (FacebookApiConfig.FacebookUser u : facebookApiConfig.getUsers()) {
            APIContext context = new APIContext(u.getAccessToken(), u.getAppSecret(), u.getAppId());

            User user = new User(u.getUserId(), context);
            User.APIRequestGetAdAccounts adAccountsExecute = user.getAdAccounts()
                .requestSpendCapField()
                .requestAccountIdField()
                .requestNameField()
                .requestAmountSpentField()
                .requestCurrencyField();

            try {
                // TODO 分页
                APINodeList<AdAccount> adAccounts = adAccountsExecute.execute();
                for (AdAccount adAccount : adAccounts) {
                    AdAccount.APIRequestGetInsights insights =
                        adAccount.getInsights().setDatePreset("maximum").requestField("spend");
                    APINodeList<AdsInsights> adsInsights = insights.execute();
                    double costAmount = 0;
                    for (AdsInsights adsInsight : adsInsights) {
                        if (adsInsight.getFieldSpend() != null) {
                            costAmount += Double.parseDouble(adsInsight.getFieldSpend());
                        }
                    }
                    CustomerAccount customerAccount = CustomerAccount.builder()
                        .accountId(adAccount.getFieldAccountId())
                        .accountName(adAccount.getFieldName())
                        .costAmount(costAmount)
                        .platform(CustomerAccount.Platform.Facebook.name())
                        .currency(adAccount.getFieldCurrency())
                        .build();
                    if (adAccount.getFieldSpendCap() != null) {
                        customerAccount.setBudgetLimit(Double.parseDouble(adAccount.getFieldSpendCap()) / 100);
                        if (adAccount.getFieldAmountSpent() != null) {
                            customerAccount.setRemainingAmount((Double.parseDouble(adAccount.getFieldSpendCap())
                                - Double.parseDouble(adAccount.getFieldAmountSpent())) / 100);
                        }
                    }

                    customerAccountService.upsert(adAccount.getFieldAccountId(), customerAccount);

                    // 每日流水
                    AdAccount.APIRequestGetInsights todayInsightsApi = adAccount.getInsights()
                        .setDatePreset(AdsInsights.EnumDatePreset.VALUE_TODAY).requestField("spend");
                    APINodeList<AdsInsights> todayInsights = todayInsightsApi.execute();
                    double todayCost = 0;
                    LocalDate today = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String date = today.format(formatter);
                    for (AdsInsights todayInsight : todayInsights) {
                        todayCost += Double.parseDouble(todayInsight.getFieldSpend());
                    }
                    accountBillService.upsert(
                        date,
                        AccountBill.builder().date(date).accountId(adAccount.getFieldAccountId()).amount(todayCost)
                            .platform(CustomerAccount.Platform.Facebook.name()).build()
                    );

                }

            } catch (APIException e) {
                // TODO log
                e.printStackTrace();
            }
        }

    }

}
