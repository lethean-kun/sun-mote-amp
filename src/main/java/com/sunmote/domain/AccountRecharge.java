package com.sunmote.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("AccountRecharge")
public class AccountRecharge {
    private Long id;
    private String accountId;
    private Double rechargeAmount;

    private Long status;
    private Date createdAt;
    private Date updatedAt;
}
