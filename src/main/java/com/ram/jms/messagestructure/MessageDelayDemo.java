package com.ram.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageDelayDemo {
    public static void main(String[] args) throws NamingException, InterruptedException {
        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = activeMQConnectionFactory.createContext()) {

            JMSProducer jmsProducer = jmsContext.createProducer();
            System.out.println("Hey Ram hello\n");
            jmsProducer.setDeliveryDelay(10000);
            jmsProducer.send(queue, "Hey Ram!");

            Message messageReceived = jmsContext.createConsumer(queue).receive();
            System.out.println(messageReceived);

        }
    }
}
