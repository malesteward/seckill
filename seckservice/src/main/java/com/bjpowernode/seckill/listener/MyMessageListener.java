package com.bjpowernode.seckill.listener;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.seckill.model.Orders;
import com.bjpowernode.seckill.orderService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * ClassName:MyMessageListener
 * Package:com.bjpowernode.activemq.listener
 * Description:
 *
 * @date:2018/12/13 10:54
 * @author:www.bjpowernode.com
 */
@Component
public class MyMessageListener implements MessageListener {

    @Autowired
    private OrderService orderService;

    /**
     * 消息监听器监听到消息后，会回调该onMessage方法，同时会把消息传给该方法
     *
     * @param message
     */
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String ordersJSON = ((TextMessage) message).getText();
                System.out.println("异步接收到的订单消息为：" + ordersJSON);

                //下订单
                Orders orders = JSONObject.parseObject(ordersJSON, Orders.class);

                try {

                    orderService.addOrders(orders);

                } catch (Exception e) {
                    e.printStackTrace();
                    //下单失败，在catch中是可以写少量逻辑代码的，主要是做一些恢复、回滚等逻辑，这种方式在开源项目代码中都有先例
                    //并不是我们第一次这样写代码

                    //1、限流列表需要删除一个元素
                    //2、用户已经购买的标记要删除
                    //3、库存要恢复回去一个
                    orderService.processOrderFail(orders);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}