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
@TableName("User")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long customerId;
    private String username;
    private String password;

    private Long status;
    private Role role;
    private Date createdAt;
    private Date updatedAt;

    public enum Role {
        USER,
        ADMIN
    }
}
