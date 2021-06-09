package com.assignment.microservices.messaging;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * MessageListenerContainerFactory is an extension of SimpleRabbitListenerContainerFactory, that scans for
 * RabbitListener annotation and constructs MessageListenerContainer instances that further manager Message Consumers as
 * per the properties specified in property file for following properties;-
 * <li>messaging.{queueName}.concurrentconsumers</li>
 * <li>messaging.{queueName}.maxconsumers</li>
 * <li>messaging.{queueName}.startConsumerInterval</li>
 * <li>messaging.{queueName}.stopConsumerInterval</li>
 * 
 */
@Component
public class MessageListenerContainerFactory extends SimpleRabbitListenerContainerFactory {

    /** The Constant MESSAGING_PROPERTIES_PREFIX for application properties. */
    private static final String MESSAGING_PROPERTIES_PREFIX = "messaging.";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListenerContainerFactory.class);

    /** The environment variable . */
    @Autowired
    private Environment env;

    @Override
    protected void initializeContainer(final SimpleMessageListenerContainer instance, RabbitListenerEndpoint endpoint) {
        super.initializeContainer(instance, endpoint);

        String queueName = instance.getQueueNames()[0];

        if (StringUtils.isEmpty(queueName)) {
            throw new IllegalArgumentException("Empty queue name for listener");
        }

        LOGGER.debug("Constructing listener container bound with queue : {}", queueName);

        final String concurrentConsumers = getMandatoryProperty(MESSAGING_PROPERTIES_PREFIX + queueName
            + ".concurrentconsumers");
        final String maxCons = getMandatoryProperty(MESSAGING_PROPERTIES_PREFIX + queueName + ".maxconsumers");
        final String startConsumerInterval = getOptionalProperty(MESSAGING_PROPERTIES_PREFIX + queueName
            + ".startConsumerInterval", MessagingConstants.DEFAULT_START_CONS_INTERVAL);
        final String stopConsumerInterval = getOptionalProperty(MESSAGING_PROPERTIES_PREFIX + queueName
            + ".stopConsumerInterval", MessagingConstants.DEFAULT_STOP_CONS_INTERVAL);

        LOGGER.debug("Properties for queue : {} concurrentconsumers:{} , maxconsumers: {},"
            + " startConsumerInterval:{}, stopConsumerInterval:{}", queueName, concurrentConsumers, maxCons,
            startConsumerInterval, stopConsumerInterval);

        instance.setConcurrentConsumers(Integer.parseInt(concurrentConsumers));
        instance.setMaxConcurrentConsumers(Integer.parseInt(maxCons));
        instance.setStartConsumerMinInterval(Integer.parseInt(startConsumerInterval));
        instance.setStopConsumerMinInterval(Integer.parseInt(stopConsumerInterval));

        final Integer maxRetryAttempt = Integer.parseInt(getOptionalProperty(MESSAGING_PROPERTIES_PREFIX + queueName
            + ".maxRetryAttempt", MessagingConstants.DEFAULT_RETRY_ATTEMPT));
        final Long retryDelay = Long.parseLong(getOptionalProperty(MESSAGING_PROPERTIES_PREFIX + queueName
            + ".retryDelay", MessagingConstants.DEFAULT_RETRY_DELAY));
        if (maxRetryAttempt == 0) {
            instance.setDefaultRequeueRejected(false);
        } else {
            FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
            fixedBackOffPolicy.setBackOffPeriod(retryDelay);
            instance.setAdviceChain(new Advice[] { RetryInterceptorBuilder.stateless().maxAttempts(maxRetryAttempt)
                .backOffPolicy(fixedBackOffPolicy).recoverer(new RejectAndDontRequeueRecoverer()).build() });
        }

    }

    /**
     * Gets the optional property from application property file.
     *
     * @param propertyKey
     *            the property key
     * @param defaultValue
     *            the default value
     * @return the optional property
     */
    private String getOptionalProperty(final String propertyKey, final String defaultValue) {
        String value = defaultValue;
        final String propertyVal = env.getProperty(propertyKey);

        if (!StringUtils.isEmpty(propertyVal)) {
            value = propertyVal;
        }
        return value;
    }

    /**
     * Gets the mandatory property from application properties, and throws {@link IllegalArgumentException} if the
     * property is missing.
     *
     * @param propertyKey
     *            the property key
     * @return the mandatory property
     */
    public String getMandatoryProperty(final String propertyKey) {
        final String propertyVal = env.getProperty(propertyKey);
        if (!StringUtils.isEmpty(propertyVal)) {
            return propertyVal;
        } else {
            throw new IllegalArgumentException("Property missing: " + propertyKey);
        }
    }

}
