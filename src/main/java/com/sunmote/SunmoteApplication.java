package com.sunmote;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@MapperScan("com.sunmote.dao")
public class SunmoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SunmoteApplication.class, args);
	}

}