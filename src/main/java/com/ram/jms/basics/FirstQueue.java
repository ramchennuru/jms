package com.ram.jms.basics;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {
    public static void main(String[] args) {
        InitialContext initialContext=null;
        Connection connection=null;
        try {
            initialContext =new InitialContext();
            ConnectionFactory connectionFactory= (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection=connectionFactory.createConnection();
            Session session=connection.createSession();
            Queue queue= (Queue) initialContext.lookup("queue/myQueue");
            MessageProducer messageProducer=session.createProducer(queue);
            TextMessage textMessage=session.createTextMessage("Hi Man This ur Friend");
            messageProducer.send(textMessage);
            System.out.println("Message sent"+textMessage.getText());

            MessageConsumer messageConsumer=session.createConsumer(queue);
            connection.start();
            TextMessage messageReceived= (TextMessage) messageConsumer.receive(500);
            System.out.println("message Received"+messageReceived.getText());
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        finally
        {
            if(initialContext!=null)
            {
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
            }
            if(connection!=null)
            {
                try {
                    connection.close();
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
