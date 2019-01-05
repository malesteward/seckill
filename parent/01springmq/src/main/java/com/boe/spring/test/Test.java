package com.boe.spring.test;/*
 *ClassName:Test
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/13 17:24
 *@author:tang@qq.com
 */

import com.boe.spring.recevie.Recevice;
import com.boe.spring.send.MessageService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        String path = "applinocation.xml";
        ClassPathXmlApplicationContext classPath = new ClassPathXmlApplicationContext(path);
        MessageService messageService = classPath.getBean("messageService", MessageService.class);
        while (true) {
            messageService.sendMessage();

        }
        /*Recevice ecevice = classPath.getBean("recevice", Recevice.class);
        ecevice.recevice();*/
    }
}
