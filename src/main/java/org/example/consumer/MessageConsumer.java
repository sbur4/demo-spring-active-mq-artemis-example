package org.example.consumer;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;
import java.util.UUID;

@Component
public class MessageConsumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    // 1
    @JmsListener(destination = "queue.sample")
    public void receiveMessage(String message) {
        System.out.println("Received 1: " + message);
    }

    // 2
    @JmsListener(destination = "queue.sample.2")
    public void receiveMessage3(String message) {
        System.out.println("Received 2: " + message);
        String uuid = UUID.randomUUID().toString();
        jmsTemplate.convertAndSend("queue.sample.3", message + "." + uuid);
    }

    // 3
    @JmsListener(destination = "queue.sample.4")
    public void receiveAndConvertMessage() {
        String message = (String) jmsTemplate.receiveAndConvert("queue.sample.4");
        String uuid = UUID.randomUUID().toString();
        System.out.println("Received 3: " + message + "." + uuid);
    }

    // 4
    @SneakyThrows
    @JmsListener(destination = "queue.sample.5")
//    @SendTo( "queue.sample.5")
    public String receiveAndConvertMessage2(String message) {
//        String message = ((TextMessage) jmsTemplate.receive("queue.sample.5")).getText();
        String uuid = UUID.randomUUID().toString();
        String m = message + "." + uuid;
        System.out.println("Received 4: " + m);
//        sendMessage4("queue.sample.5", m);
        return m;
    }

    //    @SendTo("queue.sample.5")
    public void sendMessage4(String destination, String messageContent) {
        jmsTemplate.send(destination, session -> {
            TextMessage message = session.createTextMessage(messageContent);
            return message;
        });
    }

//    @SneakyThrows
//    @JmsListener(destination = "queue.sample.5")
//    public String receiveMessage4(String destination) {
//        Message message = jmsTemplate.receive(destination);
//        if (message instanceof TextMessage) {
//            try {
//                return ((TextMessage) message).getText();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//        return null;
//        return ((TextMessage) jmsTemplate.receive(destination)).getText();
//    }
}