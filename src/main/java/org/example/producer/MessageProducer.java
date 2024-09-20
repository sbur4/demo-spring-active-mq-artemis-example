package org.example.producer;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

@Component
public class MessageProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    // 1
    public void convertAndSendMessage(String destination, String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    // 2
    public String sendAndReceiveSimpleMessage(String destination, String message) {
        jmsTemplate.convertAndSend(destination, message);
        String receivedMessage = (String) jmsTemplate.receiveAndConvert("queue.sample.3");
        return receivedMessage;
    }

    // 3
    public void sendMessage(String destination, String messageContent) {
        jmsTemplate.send(destination, session -> {
            TextMessage message = session.createTextMessage(messageContent);
            return message;
        });
    }

    // 4
    @SneakyThrows
    public String sendAndReceiveMessage(String destinationName, String messageContent) {
//        return ((TextMessage) jmsTemplate.sendAndReceive(destinationName, session -> {
//            TextMessage message = session.createTextMessage(messageContent);
//            return message;
//        })).getText();

        Message received = jmsTemplate.sendAndReceive(destinationName, session -> {
            ObjectMessage message = session.createObjectMessage(messageContent);
            return message;
        });

        return  ((TextMessage) received).getText();
    }
}