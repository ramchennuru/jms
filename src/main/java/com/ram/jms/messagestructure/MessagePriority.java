package com.ram.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessagePriority {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context=new InitialContext();
        Queue queue= (Queue) context.lookup("queue/myQueue");
        try(ActiveMQConnectionFactory cf=new ActiveMQConnectionFactory();
            JMSContext jmsContext=  cf.createContext()){

            JMSProducer jmsProducer=jmsContext.createProducer();

            String[] msg=new String[3];

            msg[0]="one";
            msg[1]="two";
            msg[2]="three";

            jmsProducer.setPriority(2);
            jmsProducer.send(queue,msg[0]);
            jmsProducer.setPriority(3);
            jmsProducer.send(queue,msg[1]);
            jmsProducer.setPriority(1);
            jmsProducer.send(queue,msg[2]);

            JMSConsumer jmsConsumer=jmsContext.createConsumer(queue);

            for(int i=0;i<3;i++)
            {
                System.out.println(jmsConsumer.receiveBody(String.class));
            }



        }


        }

}
