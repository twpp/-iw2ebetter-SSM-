package com.myproject.iw2ebetter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewControllerConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addRedirectViewController("/index","index");
        //registry.addRedirectViewController("/index.html","index");
        //registry.addRedirectViewController("/login","user/login");
        //registry.addRedirectViewController("/toLogin","/login");
        //registry.addRedirectViewController("/toRegister","/register");
        //registry.addRedirectViewController("/register","register");
        //registry.addRedirectViewController("/failLogin","/login");
        //registry.addRedirectViewController("/successLogin","/index");
        //registry.addRedirectViewController("/toSquare","square/square.html");

    }
}
