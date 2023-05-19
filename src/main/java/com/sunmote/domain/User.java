package com.sunmote.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("User")
public class User {
    private Long id;
    private String username;
    private String password;
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
    private Role role;
    private Date createdAt;
    private Date updatedAt;

    public enum Role {
        USER,
        ADMIN
    }
}
