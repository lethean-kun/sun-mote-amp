package com.sunmote.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthSuccessHandler authSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthnUserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    // 授权相关操作
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http
            // 关闭csrf，此时登出logout接收任何形式的请求；（默认开启，logout只接受post请求）
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/js/**")
            .permitAll()
            .antMatchers("/css/**")
            .permitAll()
            .antMatchers("/fonts/**")
            .permitAll()
            .antMatchers("/vendor/**")
            .permitAll()
            .antMatchers("/images/**")
            .permitAll()
            // admin页面，只有admin角色可以访问
            .antMatchers("/admin").hasAnyAuthority("ADMIN")
            // home 页面，ADMIN 和 USER 都可以访问
            .antMatchers("/users").hasAnyAuthority("USER", "ADMIN")
            // login 页面，所有用户都可以访问
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
            .and()
            // 自定义登录表单
            .formLogin().loginPage("/login").successHandler(authSuccessHandler)
            // 失败跳转的页面（比如用户名/密码错误），这里还是跳转到login页面，只是给出错误提示
            .failureUrl("/login?error=true")
            .and()
            // 登出 所有用户都可以访问
            .logout().permitAll()
            .and()
            // 权限不足时跳转的页面，即访问一个页面时没有对应的权限，会跳转到这个页面
            .exceptionHandling().accessDeniedPage("/accessDenied");
    }

    // 定义一个密码加密器，这个BCrypt也是Spring默认的加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
