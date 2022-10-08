package com.sunmote.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountApproval {
    private Long id;
    private String timeZone;
    private String accountId;
    private String prodLink;
    private Long userId;

    private Long status;
    private Date createdAt;
    private Date updatedAt;
    // 冗余字段 处理首次开会充值
    private Double rechargeAmount;
    private String currency;
    // 汇率
    private Double exchangeRate;

    // FE
    private String username;

}
