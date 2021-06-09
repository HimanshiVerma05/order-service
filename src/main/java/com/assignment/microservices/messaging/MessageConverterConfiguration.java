package com.assignment.microservices.messaging;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class MessageConverterConfiguration configures the JSON messaging converter for template publishing and
 * consumption.
 */
@Configuration
public class MessageConverterConfiguration {

    /** The object mapper. */
    @Autowired
    ObjectMapper objectMapper;

    /**
     * Json message converter.
     *
     * @return the message converter
     */
    @Bean
    @Primary
    public MessageConverter jsonMessageConverterCustom() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
