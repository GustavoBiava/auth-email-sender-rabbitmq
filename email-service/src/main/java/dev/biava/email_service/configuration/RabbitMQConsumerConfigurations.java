package dev.biava.email_service.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfigurations {
    
    @Value("${app.rabbitmq.queue.email}")
    private String emailQueue;

    @Value("${app.rabbitmq.exchange.email}")
    private String emailExchange;

    @Value("${app.rabbitmq.routingkey.email}")
    private String emailRoutingKey;

    @Value("${app.rabbitmq.queue.email-dlq}")
    private String emailDLQueue;

    @Value("${app.rabbitmq.exchange.email-dlx}")
    private String emailDLExchange;

    @Value("${app.rabbitmq.routingkey.email-dlq}")
    private String dlqRoutingKey;

    @Bean
    public Queue emailDLQueue() {
        return QueueBuilder.durable(emailQueue)
                .withArgument("x-dead-letter-exchange", emailDLExchange)
                .withArgument("x-dead-letter-routing-key", dlqRoutingKey)
                .build();
    } 

    @Bean
    public DirectExchange emailDLExchange() {
        return new DirectExchange(emailDLExchange);
    }

    @Bean
    public Binding bindingEmailDLQ(Queue emailDLQueue, DirectExchange emailDLExchange) {
        return BindingBuilder.bind(emailDLQueue).to(emailDLExchange).with(dlqRoutingKey);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue, true);
    } 

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(emailExchange);
    }

    @Bean
    public Binding bindingEmail(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(emailRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

}
