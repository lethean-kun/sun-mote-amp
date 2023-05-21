package com.sunmote.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("AccountBill")
@Builder
public class AccountBill {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String accountId;
    private String platform;
    // 花费额
    private double amount;
    // 日期 格式 YYYY-MM-DD
    private String date;

    private Long status;
    private Date createdAt;
    private Date updatedAt;
}
