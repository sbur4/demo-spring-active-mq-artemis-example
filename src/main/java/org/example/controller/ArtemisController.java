package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArtemisController {
    @Autowired
    private MessageProducer messageProducer;

    // 1
    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message must not be empty");
        }

        String destinationQueue = "queue.sample";  // Define the actual queue name here
        messageProducer.convertAndSendMessage(destinationQueue, message);
        return "Message sent to 1 \"" + destinationQueue + "\": " + message;
    }

    // 2
    @PostMapping("/sendAndReceive")
    public String sendAndReceiveSimple(@RequestParam("message") String message) {
        String destinationQueue = "queue.sample.2";
        String newMessage = messageProducer.sendAndReceiveSimpleMessage(destinationQueue, message);
        return "Message sent to 2 \"" + destinationQueue + "\": " + newMessage;
    }

    // 3
    @PostMapping("/send2")
    public String sendMessage2(@RequestParam("message") String message) {
        String destinationQueue = "queue.sample.4";
        messageProducer.sendMessage(destinationQueue, message);
        return "Message sent to 3 \"" + destinationQueue + "\": " + message;
    }

    // 4
    @PostMapping("/send3")
    public String sendMessage3(@RequestParam("message") String message) {
        String destinationQueue = "queue.sample.5";
        String m = messageProducer.sendAndReceiveMessage(destinationQueue, message);
        return "Message sent to 4 \"" + destinationQueue + "\": " + m;
    }
}