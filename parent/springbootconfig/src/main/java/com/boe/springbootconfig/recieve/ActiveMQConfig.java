package com.boe.springbootconfig.recieve;/*
 *ClassName:ActiveMQConfig
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/14 16:01
 *@author:tang@qq.com
 */
import com.boe.springbootconfig.lisnter.Lister;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.annotation.ManagedBean;
import javax.jms.ConnectionFactory;

@Configuration
public class ActiveMQConfig {

    @Autowired
    private ActiveMQConnectionFactory connectionFactory;

    @Autowired
    private Lister lister;

    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer(){
        DefaultMessageListenerContainer defaultMessage = new DefaultMessageListenerContainer();
        defaultMessage.setConnectionFactory(connectionFactory);
        defaultMessage.setDestinationName("tang");
        defaultMessage.setMessageListener(lister);
        return defaultMessage;
    }
}
