package com.boe.springbootmulti;

import com.boe.springbootmulti.dynamic.DynamicDataSource;
import com.boe.springbootmulti.dynamic.ThreadLocalHoder;
import com.boe.springbootmulti.model.User;
import com.boe.springbootmulti.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringbootmultiApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootmultiApplication.class, args);
        ThreadLocalHoder.setDatSourceKey(DynamicDataSource.DB3307);
        UserService userService = applicationContext.getBean("userService",UserService.class);
        User user = new User();
        user.setId(6);
        user.setName("宋智孝");
        user.setAge("28");
        int count = userService.updateUserMapper3307(user);
        System.out.println("user3307------" +count);
    }

}

