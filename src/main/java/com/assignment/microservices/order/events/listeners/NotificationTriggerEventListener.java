package com.assignment.microservices.order.events.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.assignment.microservices.messaging.MessagingTemplate;
import com.assignment.microservices.order.exception.BusinessException;
import com.assignment.microservices.order.notification.NotificationTrigger;
import com.assignment.microservices.order.response.BaseResponse;


/**
 * The listener interface for receiving notificationTriggerEvent events. The class that is interested in processing a
 * notificationTriggerEvent event implements this interface, and the object created with that class is registered with a
 * component using the component's <code>addNotificationTriggerEventListener<code> method. When the
 * notificationTriggerEvent event occurs, that object's appropriate method is invoked.
 *
 * @author Himanshi verma
 */
@Component
public class NotificationTriggerEventListener {

    /** The notification exchange. */
    @Value("${notification.messaging.queue.exchange}")
    private String notificaitonExchange;

    /** The notification routing key. */
    @Value("${notification.messaging.queue.routingkey}")
    private String notificationRoutingKey;

    /** The messaging template. */
    @Autowired(required=false)
    private MessagingTemplate messagingTemplate;

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTriggerEventListener.class);

    /**
     * Handle event notification trigger and publish it to the queue.
     *
     * @param notificationTrigger
     *            the notification trigger
     * @throws NGMBusinessException
     *             the NGM business exception if error converting object to json
     */
    @EventListener
    public void handleEvent(final String params) throws BusinessException {

        LOGGER.debug("Publishing event notification for pnr with routing key", notificationRoutingKey);
       

        this.messagingTemplate.publishMessage(notificaitonExchange, notificationRoutingKey, params);
    }

}
