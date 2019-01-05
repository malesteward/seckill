package send;/*
 *ClassName:ActivemaSend
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/11 15:18
 *@author:tang@qq.com
 */

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActivemaSend {

    public static final String URL = "tcp://192.168.68.128:61616";
    public static final String DESTINATION_NAME = "myTopic";


    public static void main(String[] args) {
        send();
    }

    public static void send() {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
       // QueueConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory(URL);
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            Message info = session.createTextMessage("a girl");
            Destination destination = session.createQueue(DESTINATION_NAME);
            producer = session.createProducer(destination);
            info.setStringProperty("name","啊");

            producer.send(info);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (  connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (  session != null){
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (  producer != null){
                try {
                    producer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
