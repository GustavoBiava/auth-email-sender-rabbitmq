package dev.biava.auth.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.biava.auth.domain.Email.EmailDTO;

@Service
public class EmailService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange.email}")
    private String emailExchange;

    @Value("${app.rabbitmq.routingkey.email}")
    private String emailRoutingKey;

    public void publishEmailEvent(EmailDTO emailDTO) {
        rabbitTemplate.convertAndSend(this.emailExchange, this.emailRoutingKey, emailDTO);
    }

}
