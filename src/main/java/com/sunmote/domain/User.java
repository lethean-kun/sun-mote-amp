package com.sunmote.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String corpName;
    private Long status;
    private Role role;
    private Double rebate;

    private Date createdAt;
    private Date updatedAt;

    public enum Role {
        USER,
        ADMIN
    }
}
