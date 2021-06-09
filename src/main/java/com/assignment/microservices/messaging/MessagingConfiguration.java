package com.assignment.microservices.messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * MessagingConfiguration Javaconfig class.
 */
@Configuration
public class MessagingConfiguration {

    /** The factory. */
    @Autowired
    ConnectionFactory factory;

    /** The configurer. */
    @Autowired
    SimpleRabbitListenerContainerFactoryConfigurer configurer;

    /** The converter. */
    @Autowired
    MessageConverter converter;

    /** The listener container factory. */
    @Autowired
    MessageListenerContainerFactory listenerContainerFactory;

    /**
     * Rabbit listener container factory, for custom construction of MessageListenerContainers.
     *
     * @return the simple rabbit listener container factory
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        configurer.configure(listenerContainerFactory, factory);
        return listenerContainerFactory;
    }

    /**
     * Amqp template with json converter.
     *
     * @return the amqp template
     */
    @Bean
    @Primary
    AmqpTemplate template() {
        final RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(converter);
        return template;
    }

}
