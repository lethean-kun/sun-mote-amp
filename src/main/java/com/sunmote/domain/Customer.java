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
@TableName("Customer")
public class Customer {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String corpName;
    private String corpSubject;
    private String cooperationMode;
    private String settlement;
    private String currency;
    private Double serviceFee;
    private Double rebate;
    private String remarks;
    private String source;

    private Long status;
    private Date createdAt;
    private Date updatedAt;
}
