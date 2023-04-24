package com.ram.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageTypesDemo {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = activeMQConnectionFactory.createContext()) {

            JMSProducer jmsProducer = jmsContext.createProducer();
            BytesMessage bytesMessage= jmsContext.createBytesMessage();
            bytesMessage.writeUTF("john");
            bytesMessage.writeLong(123l);

            jmsProducer.send(queue, bytesMessage);

            BytesMessage messageReceived = (BytesMessage) jmsContext.createConsumer(queue).receive();
            System.out.println(messageReceived.readLong());
            System.out.println(messageReceived.readUTF());
        }
    }
}
