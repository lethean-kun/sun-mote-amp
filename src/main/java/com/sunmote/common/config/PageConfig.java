package com.sunmote.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageConfig {

    final ApplicationContext applicationContext;

    public PageConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public void paginationInterceptorPageLimit() {
        PaginationInterceptor bean = applicationContext.getBean(PaginationInterceptor.class);
        bean.setLimit(-1);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
