package com.sunmote.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @author zongzekun
 * @create 2023-05-18 11:47
 */
@Component public class SyncSchedule {

    @Scheduled(fixedRate = 60000)
    public void syncAccountBill() {
        System.out.println("一分钟heartbeat");
    }

}
