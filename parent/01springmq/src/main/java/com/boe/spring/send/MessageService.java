package com.boe.spring.send;/*
 *ClassName:MessageService
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/13 17:19
 *@author:tang@qq.com
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class MessageService {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(){
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("a girl");
            }
        });
        return ;
    }

}
