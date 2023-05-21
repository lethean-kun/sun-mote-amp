package com.sunmote.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("CustomerAccount")
@Builder
public class CustomerAccount {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long customerId;
    private String accountId;
    private String accountName;
    private String platform;
    // 预算总额
    private double budgetLimit;
    // 剩余预算
    private double remainingAmount;
    // 花费总额
    private double costAmount;
    // 币种
    private String currency;

    private Long status;
    private Date createdAt;
    private Date updatedAt;

    // 广告花费
    @TableField(exist = false)
    private double adCost;

    // 充值总额
    @TableField(exist = false)
    private double accountRechargeAmount;

    public enum Platform {
        Facebook,
        Google
    }
}
