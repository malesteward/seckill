package com.bjpowernode.seckill.config;

import com.bjpowernode.seckill.listener.MyMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;

/**
 * ClassName:ActiveMQConfig
 * Package:com.bjpowernode.activemq.config
 * Description:
 *
 * @date:2018/12/14 9:06
 * @author:www.bjpowernode.com
 */
@Configuration // == xml
public class ActiveMQConfig {

    @Value("${spring.jms.template.default-destination}")
    private String destination;

    @Value("${spring.jms.pub-sub-domain}")
    public boolean pubSubDomain;

    /**
     *     <!-- 配置一个连接工厂 -->
     *     <bean id="connectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
     *         <property name="brokerURL" value="tcp://192.168.220.128:61616"/>
     *     </bean>
     *
     *     springboot已经自动配置好了的，所以我们不用单独再配置
     *     1、加入了spring-jms依赖
     *     2、再application.properies文件配置了连接信息
     *     springboot会自动配置好connectionFactory
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     *     <!-- 我们自定义的一个消息监听器 -->
     *     <bean id="myMessageListener" class="com.bjpowernode.activemq.listener.MyMessageListener" />
     */
    @Autowired
    private MyMessageListener myMessageListener;

    /**
     *     <!-- 要实现消息的异步接收，需要配置一个sping监听器的容器bean -->
     *     <bean class="org.springframework.jms.listener.DefaultMessageListenerContainer">
     *         <property name="connectionFactory" ref="connectionFactory"/>
     *         <!--消息的目的地-->
     *         <property name="destinationName" value="springQueue"/>
     *         <!--当监听到消息后，回调哪个onMessage方法？ 需要提供一个监听器类-->
     *         <property name="messageListener" ref="myMessageListener" />
     *
     *         <!--pubSubDomain=false表示点对点消息，true表示发布订阅消息，默认是false-->
     *         <property name="pubSubDomain" value="true"/>
     *     </bean>
     */
    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer() {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
        defaultMessageListenerContainer.setDestinationName(destination);
        defaultMessageListenerContainer.setMessageListener(myMessageListener);
        defaultMessageListenerContainer.setPubSubDomain(pubSubDomain);
        defaultMessageListenerContainer.setConcurrentConsumers(16);//设置消费者个数，适合高并发场景下，发送消息太多了，消费不过来
        return defaultMessageListenerContainer;
    }
}