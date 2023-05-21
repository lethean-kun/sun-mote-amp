package com.sunmote.schedule;

import com.facebook.ads.sdk.*;
import com.sunmote.domain.AccountBill;
import com.sunmote.domain.CustomerAccount;
import com.sunmote.service.AccountBillService;
import com.sunmote.service.CustomerAccountService;
import org.springframework.scheduling.annotation.Scheduled;
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

    public SyncSchedule(CustomerAccountService customerAccountService, AccountBillService accountBillService) {
        this.customerAccountService = customerAccountService;
        this.accountBillService = accountBillService;
    }


    @Scheduled(fixedRate = 600000)
    public void syncAccountBill() {
        APIContext context = new APIContext("EAAUOWMLPchgBACZCBxJiN1EJWJviIP4lU3RDlACp3O4fdsZAWDOg1Y5ZCQwM9RfMNDoOmpPaJVCFeXEk75HD2WYP5jZAuJawCQEY4wrrEWUzgt9EeipOwbWAFFfmx5dq4oWwas5SBcg1l8ZAEnUt2qsZA83xKZC8I0Bbc7XJqWfTfgJRDE3t9FgZCoAmxZCHVA4IZD");
        User user = new User("100092998956741", context);
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
                AdAccount.APIRequestGetInsights insights = adAccount.getInsights().setDatePreset("maximum").requestField("spend");
                APINodeList<AdsInsights> adsInsights = insights.execute();
                double costAmount = 0;
                for (AdsInsights adsInsight : adsInsights) {
                    costAmount += Double.parseDouble(adsInsight.getFieldSpend());
                }
                CustomerAccount customerAccount = CustomerAccount.builder()
                        .accountId(adAccount.getFieldAccountId())
                        .accountName(adAccount.getFieldName())
                        .costAmount(costAmount)
                        .budgetLimit(Double.parseDouble(adAccount.getFieldSpendCap()) / 100)
                        .remainingAmount((Double.parseDouble(adAccount.getFieldSpendCap()) - Double.parseDouble(adAccount.getFieldAmountSpent())) / 100)
                        .platform(CustomerAccount.Platform.Facebook)
                        .currency(adAccount.getFieldCurrency())
                        .build();

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
                        AccountBill.builder().date(date).amount(todayCost).platform(CustomerAccount.Platform.Facebook.name()).build()
                );

            }

        } catch (APIException e) {
            // TODO log
            e.printStackTrace();
        }
    }

}
