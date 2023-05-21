package com.sunmote.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "facebook")
public class FacebookApiConfig {
    private List<FacebookUser> users;


    @Data
    public static class FacebookUser {
        private String accessToken;
        private String appSecret;
        private String appId;
        private String userId;
    }


}
