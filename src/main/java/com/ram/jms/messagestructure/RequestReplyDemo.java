package com.ram.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class RequestReplyDemo {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/requestQueue");
        //Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");

        try(ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = activeMQConnectionFactory.createContext()){

            JMSProducer jmsProducer=jmsContext.createProducer();
            TemporaryQueue replyQueue=jmsContext.createTemporaryQueue();
            TextMessage textMessage=jmsContext.createTextMessage("Hi Good morning!");
            textMessage.setJMSReplyTo(replyQueue);
            jmsProducer.send( queue,textMessage);
            System.out.println(textMessage.getJMSMessageID());

            JMSConsumer jmsConsumer=jmsContext.createConsumer(queue);
            TextMessage textMessagerecieved= (TextMessage) jmsConsumer.receive();
            System.out.println(textMessagerecieved.getText());

            JMSProducer replyProducer=jmsContext.createProducer();
            TextMessage replyMessage=jmsContext.createTextMessage("Hey Man!");
            replyMessage.setJMSCorrelationID(textMessagerecieved.getJMSMessageID());
            replyProducer.send(textMessagerecieved.getJMSReplyTo(),replyMessage);

            JMSConsumer jmsConsumer1=jmsContext.createConsumer(replyQueue);
            TextMessage textMessagerecieved1= (TextMessage) jmsConsumer1.receive();
            System.out.println(textMessagerecieved1.getJMSCorrelationID());




        }
    }

}
