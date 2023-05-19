package com.sunmote.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRechargeApproval {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String accountId;
    // 目标币种
    private String targetCurrency;
    // 充值金额
    private Double rechargeAmount;
    // 实际支付金额
    private Double payAmount;
    // 汇率
    private Double exchangeRate;
    // 汇率结算金额
    private Double targetCurrencyAmount;

    private Long userId;
    private Long status;

    private Date createdAt;
    private Date updatedAt;

    // FE
    private String username;

}
