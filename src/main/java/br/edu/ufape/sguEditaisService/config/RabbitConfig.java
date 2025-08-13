package br.edu.ufape.sguEditaisService.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "auth-role-exchange";

    @Bean
    public TopicExchange exchange() { return new TopicExchange(EXCHANGE);}

    @Bean
    public MessageConverter jsonMessageConverter() { return new Jackson2JsonMessageConverter();}

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
       RabbitTemplate template = new RabbitTemplate(connectionFactory);
       template.setMessageConverter(jsonMessageConverter());
       return template;
    }
}
