package com.dd.electronicbusiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.EnableCaching;
@SpringBootApplication
@MapperScan("com.dd.electronicbusiness.dao")
@EnableCaching
public class ElectronicBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicBusinessApplication.class, args);
    }

}
