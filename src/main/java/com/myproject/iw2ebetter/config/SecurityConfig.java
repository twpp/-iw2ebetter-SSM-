package com.myproject.iw2ebetter.config;

import com.myproject.iw2ebetter.component.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //添加用户处理的服务类
                .userDetailsService(userDetailsService)
                //添加对应的加密器
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/register/user","/register/host","/register/admin")
                .permitAll()
                .antMatchers("/toRegister","/adminRegister","/toLogin","/usernameIsExisted").permitAll()
                .anyRequest().authenticated()
                .and()
                //formLogin使用自定义的登录表单页面
                .formLogin()
                //这个是登录页面的action中的值
                .loginPage("/toLogin")
                .passwordParameter("password")
                .usernameParameter("username")
                .failureForwardUrl("/failLogin")
                .successForwardUrl("/successLogin")
                .and()
                //.rememberMe()
                //.rememberMeParameter("remember-me")
                //.and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**","/fonts/**","/images/**","/js/**");
    }
}
