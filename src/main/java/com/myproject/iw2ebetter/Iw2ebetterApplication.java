package com.myproject.iw2ebetter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@MapperScan("com.myproject.iw2ebetter.mapper")
public class Iw2ebetterApplication {

    public static void main(String[] args) {
        SpringApplication.run(Iw2ebetterApplication.class, args);
    }

}
