package com.ram.jms.basics;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JmsContextDemo {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context=new InitialContext();
        Queue queue= (Queue) context.lookup("queue/myQueue");

        try(ActiveMQConnectionFactory cf=new ActiveMQConnectionFactory();
            JMSContext jmsContext= (JMSContext) cf.createContext()){
            jmsContext.createProducer().send(queue,"Arise awake and stop npt till the goal is reached");

            String messageReceived=jmsContext.createConsumer(queue).receiveBody(String.class);
            System.out.println("message Received"+messageReceived);


        }
    }
}
