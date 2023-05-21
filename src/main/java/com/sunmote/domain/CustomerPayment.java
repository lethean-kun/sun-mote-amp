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
@TableName("CustomerPayment")
public class CustomerPayment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String customerId;
    private String remark;
    // 充值额
    private double amount;


    private Long status;
    private Date createdAt;
    private Date updatedAt;
}
