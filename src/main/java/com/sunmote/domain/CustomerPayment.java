package com.sunmote.domain;

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
    private Long id;
    private String customerId;
    private String remark;
    // 充值额
    private String amount;


    private Long status;
    private Date createdAt;
    private Date updatedAt;
}
