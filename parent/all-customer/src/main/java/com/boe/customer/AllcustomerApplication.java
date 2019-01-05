package com.boe.customer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class AllcustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllcustomerApplication.class, args);
    }
}
