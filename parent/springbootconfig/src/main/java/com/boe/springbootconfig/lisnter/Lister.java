package com.boe.springbootconfig.lisnter;/*
 *ClassName:Lister
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/14 16:04
 *@author:tang@qq.com
 */

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Date;


@Service
public class Lister implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            String text = null;
            try {
                text = ((TextMessage) message).getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            System.out.println(text+ new Date());
        }
    }
}
