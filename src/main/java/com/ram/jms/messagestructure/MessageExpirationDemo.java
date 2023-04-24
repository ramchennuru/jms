package com.ram.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageExpirationDemo {
    public static void main(String[] args) throws NamingException, InterruptedException, JMSException {
        InitialContext initialContext = new InitialContext();
        Queue queue= (Queue) initialContext.lookup("queue/myQueue");
        Queue expiryQueue= (Queue) initialContext.lookup("queue/ExpiryQueue");

        try(ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory();
            JMSContext jmsContext= activeMQConnectionFactory.createContext()){

            JMSProducer jmsProducer= jmsContext.createProducer();
            jmsProducer.setTimeToLive(1000);
            jmsProducer.send(queue,"Hey Ram!");
            Thread.sleep(5000);
            Message messageReceived= jmsContext.createConsumer(queue).receive(5000);
            System.out.println(messageReceived);

            System.out.println(jmsContext.createConsumer(expiryQueue).receiveBody(String.class));


        }

    }
}
