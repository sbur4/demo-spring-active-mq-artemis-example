package org.example.config;

import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class ArtemisConfig {
    @Value("${spring.artemis.uri}")
    String uri;
    @Value("${spring.artemis.user}")
    String user;
    @Value("${spring.artemis.password}")
    String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQJMSConnectionFactory(uri, user, password);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(messageConverter);

        return jmsTemplate;
    }

    @Bean
    MessageConverter converter() {
        return new SimpleMessageConverter();
    }

}