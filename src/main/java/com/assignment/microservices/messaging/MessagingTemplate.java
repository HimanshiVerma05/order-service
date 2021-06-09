package com.assignment.microservices.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * The Class MessagingTemplate, as an abstraction of {@link AmqpTemplate} providing message conversion to JSON before
 * publishing messages to Queue.
 */
@Component
public class MessagingTemplate {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingTemplate.class);

    /** The Constant MSG_DELAY_KEY. */
    private static final String MSG_DELAY_KEY = "x-delay";

    /** The message queue connection factory. */
    @Autowired
    ConnectionFactory factory;

    /** The messaging template. */
    @Autowired
    AmqpTemplate amqpTemplate;

    /** The message converter. */
    @Autowired
    MessageConverter messageConverter;

    /**
     * Publish message.
     *
     * @param exchangeName
     *            the exchange name
     * @param routingKey
     *            the routing key
     * @param message
     *            the message
     */
    public void publishMessage(final String exchangeName, final String routingKey, final Object message) {
        if (message == null) {
            throw new IllegalArgumentException("Invalid message");
        }

//        LOGGER.debug("Publishing message to exchange: {}, with routingKey:{} ", exchangeName, routingKey);
//        Message m = buildMessage(message);
//
//        String token = "Himanshi";
//        
//        m.getMessageProperties().getHeaders().put(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        LOGGER.info("message publishing Himanshi");
        amqpTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    /**
     * Build Message.
     *
     * @param message
     *            the message
     * @return the message
     */
    public Message buildMessage(final Object message) {
        return messageConverter.toMessage(message, buildAuthenticationHeader());
    }

    /**
     * Build Authentication header.
     *
     * @return the message properties
     */
    private MessageProperties buildAuthenticationHeader() {
        return new MessageProperties();
    }

    /**
     * Publish message with delay.
     *
     * @param exchangeName
     *            the exchange name
     * @param routingKey
     *            the routing key
     * @param message
     *            the message
     * @param delayInMillis
     *            the delay in millis
     */
    public void publishMessageWithDelay(final String exchangeName, final String routingKey, final Object message,
        final long delayInMillis) {
        if (message == null) {
            throw new IllegalArgumentException("Invalid message");
        } else if (delayInMillis <= 0) {
            throw new IllegalArgumentException("Invalid delay in milliseconds.");
        }

        LOGGER.debug("Publishing message to exchange: {}, with routingKey:{} ", exchangeName, routingKey);
        amqpTemplate.convertAndSend(exchangeName, routingKey, buildDelayedMessage(message, delayInMillis));
    }

    /**
     * Builds the delayed message.
     *
     * @param payload
     *            the payload
     * @param delay
     *            the delay
     * @return the message
     */
    private Message buildDelayedMessage(final Object payload, final long delay) {
        MessageProperties properties = buildAuthenticationHeader();
        properties.setHeader(MSG_DELAY_KEY, delay);
        return messageConverter.toMessage(payload, properties);
    }

    /**
     * Gets the raw template.
     *
     * @return the raw template
     */
    public AmqpTemplate getRawTemplate() {
        return this.amqpTemplate;
    }

}
