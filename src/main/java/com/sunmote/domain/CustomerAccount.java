package com.sunmote.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("CustomerAccount")
public class CustomerAccount {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long customerId;
    private String accountId;
    private String accountName;
    private String platform;
    // 预算总额
    private String budgetLimit;
    // 花费总额
    private String costAmount;
    // 币种
    private String currency;

    private Long status;
    private Date createdAt;
    private Date updatedAt;
}
