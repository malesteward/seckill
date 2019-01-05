package com.boe.springboot.recivce;/*
 *ClassName:MessageReciver
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/13 20:42
 *@author:tang@qq.com
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class MessageReciver {
    @Autowired
    private JmsTemplate jmsTemplate;
    @JmsListener(destination = "${spring.jms.template.default-destination}")
    public void recevice(Message message){

        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                System.out.println("接收到的消息为：" + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
