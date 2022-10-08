package com.sunmote.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositApproval {
    private Long id;
    private String deposit;
    private String currency;
    private Long userId;
    private String remark;
    private Long status;

    private Date createdAt;
    private Date updatedAt;

    // FE
    private String username;

}
