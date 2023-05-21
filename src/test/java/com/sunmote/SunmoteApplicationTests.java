package com.sunmote;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.facebook.ads.sdk.*;
import com.sunmote.domain.CustomerAccount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SunmoteApplicationTests {

    @Test
    void contextLoads() {

        APIContext context = new APIContext("",
                "", "");
//        context.enableDebug(true);

        User user = new User("", context);
        User.APIRequestGetAdAccounts adAccountsExecute = user.getAdAccounts().requestSpendCapField();

        try {
            APINodeList<AdAccount> adAccounts = adAccountsExecute.execute();
            for (AdAccount adAccount : adAccounts) {
                AdAccount.APIRequestGetInsights insights = adAccount.getInsights()
                        .setDatePreset(AdsInsights.EnumDatePreset.VALUE_TODAY)
                        .requestField("spend");
                APINodeList<AdsInsights> adsInsights = insights.execute();
                AdAccount.APIRequestGetCampaigns getCampaigns = adAccount.getCampaigns()
                        .setDatePreset(Campaign.EnumDatePreset.VALUE_LAST_7D)
                        .requestAllFields();

                APINodeList<Campaign> campaigns = getCampaigns.execute();

                for (Campaign campaign : campaigns) {
                    Campaign execute = campaign.get().requestAllFields().execute();
//					campaign.getInsights()

                }

            }

        } catch (APIException e) {
            e.printStackTrace();
        }

    }

}
