package receive;/*
 *ClassName:ActivemaRecive
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/11 16:13
 *@author:tang@qq.com
 */

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActivemaRecive {


    public static final String URL = "tcp://192.168.68.128:61616";
    public static final String DESTINATION_NAME = "myTopic";


    public static void main(String[] args) {
        receive();
    }

    public static void receive() {
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        QueueConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory(URL);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(DESTINATION_NAME);
            String info = "name = '啊'";
            consumer = session.createConsumer(destination, info);
            // Message receive = consumer.receive();
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        String text = null;
                        try {
                            text = ((TextMessage) message).getText();

                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                        System.out.println(text);
                    }
                }
            });

        } catch (JMSException e) {
            e.printStackTrace();
        } /*finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        */

    }

}
