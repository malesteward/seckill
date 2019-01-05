package com.boe.springbootmybatis;

import com.boe.springbootmybatis.model.User;
import com.boe.springbootmybatis.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringbootmybatisApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SpringbootmybatisApplication.class, args);
        UserService userService = applicationContext.getBean("userService",UserService.class);
        User user = new User();
        user.setId(6);
        user.setName("宋智孝");
        user.setAge("28");
        int count = userService.updateUserMapper3307(user);
        System.out.println("user3307------" +count);
    }

}

