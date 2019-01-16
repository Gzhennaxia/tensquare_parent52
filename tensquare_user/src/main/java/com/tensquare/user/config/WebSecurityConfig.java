package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authorizeRequests()：所有security全注解是实现形式的开端，表示开始说明需要的权限
        // 需要的权限分为两部分，第一部分时拦截的路径，第二部分是访问该路径所需要的权限。
        // antMatchers：拦截的路径，permitAll：任何权限都可以访问，直接放行所有。
        // .and().csrf().disable()：固定写法， 表示使csrf拦截失效。
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
