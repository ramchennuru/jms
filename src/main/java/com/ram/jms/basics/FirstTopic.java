package com.ram.jms.basics;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext =new InitialContext();
        Topic topic= (Topic) initialContext.lookup("topic/myTopic");

        ConnectionFactory connectionFactory= (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        Connection connection =connectionFactory.createConnection();

        Session session=connection.createSession();
        MessageProducer messageProducer=session.createProducer(topic);

        MessageConsumer messageConsumer=session.createConsumer(topic);
        MessageConsumer messageConsumer1=session.createConsumer(topic);

        TextMessage textMessage= session.createTextMessage("All the Best Ram");

        messageProducer.send(textMessage);
        connection.start();
        TextMessage textMessage1= (TextMessage) messageConsumer.receive();

        System.out.println("consumer 1 message received"+textMessage1.getText());

        TextMessage textMessage2= (TextMessage) messageConsumer1.receive();
        System.out.println("consumer 2 message received"+textMessage2.getText());

        connection.close();
        initialContext.close();


    }


}
