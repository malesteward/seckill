package com.boe.springboot;

import com.boe.springboot.recivce.MessageReciver;
import com.boe.springboot.send.MessageSend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootmqApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configura = SpringApplication.run(SpringbootmqApplication.class, args);
        MessageSend messageSend = configura.getBean("messageSend", MessageSend.class);
       // messageSend.send();


        MessageReciver messageReciver = configura.getBean("messageReciver", MessageReciver.class);
    }

}

