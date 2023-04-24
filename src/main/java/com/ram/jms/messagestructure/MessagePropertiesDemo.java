package com.ram.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessagePropertiesDemo {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = activeMQConnectionFactory.createContext()) {

            JMSProducer jmsProducer = jmsContext.createProducer();
            TextMessage textMessage= jmsContext.createTextMessage("Hey ram How r u?");
            textMessage.setBooleanProperty("loggedIn",true);
            textMessage.setBooleanProperty("loggedIn1",false);
            textMessage.setStringProperty("userToken","abc123");
//            System.out.println("Hey Ram hello\n");
//            jmsProducer.setDeliveryDelay(10000);
            jmsProducer.send(queue, textMessage);

            Message messageReceived = jmsContext.createConsumer(queue).receive();
            System.out.println(messageReceived.getBooleanProperty("loggedIn"));
            System.out.println(messageReceived.getBooleanProperty("loggedIn1"));
            System.out.println(messageReceived.getStringProperty("userToken"));

        }
    }
}
