package com.boe.spring.recevie;/*
 *ClassName:Recevice
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/13 17:25
 *@author:tang@qq.com
 */

import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.management.j2ee.statistics.JavaMailStats;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class Recevice {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void recevice(){
        Message message =  jmsTemplate.receive();
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                System.out.println("接收到的消息为：" + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public void recevicePool(){
        int cpu = 8;
         ExecutorService executorService = Executors.newFixedThreadPool(cpu*2);
        for (int i = 0 ;i< 16 ; i++){
            executorService.submit(new Runnable() {
                public void run() {
                  while (true){
                      Message message =  jmsTemplate.receive();
                      if (message instanceof TextMessage) {
                          try {
                              String text = ((TextMessage) message).getText();
                              System.out.println("接收到的消息为：" + text+new Date());
                          } catch (JMSException e) {
                              e.printStackTrace();
                          }
                      }
                  }
                }
            });
            System.out.println(i);
        }
        if(executorService.isTerminated()){
            executorService.shutdown();
        }
    }
}
