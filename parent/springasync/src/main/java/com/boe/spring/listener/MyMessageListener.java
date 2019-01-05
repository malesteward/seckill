package com.boe.spring.listener;/*
 *ClassName:MyMessageListener
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/13 19:13
 *@author:tang@qq.com
 */

import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


public class MyMessageListener implements MessageListener {
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                System.out.println("异步接收到的消息为：" + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
