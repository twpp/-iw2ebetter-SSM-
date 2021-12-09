package com.myproject.iw2ebetter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImgBackShowConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/headImage/**").addResourceLocations("file:"+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\headImage\\");

        //这个是配置资源固定映射 服务器存储位置 如果不配置
        //上传图片之后不部署就不能回显
        registry.addResourceHandler("/images/appraise/**").addResourceLocations("file:"+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\appraise\\");

    }
}
