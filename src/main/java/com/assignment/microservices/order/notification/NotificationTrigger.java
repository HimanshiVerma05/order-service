package com.assignment.microservices.order.notification;

import java.util.Map;

import com.assignment.microservices.order.enums.EventEnum;

/**
 * Notification Trigger
 *
 * @author Himanshi verma
 *
 */
public class NotificationTrigger {

    private EventEnum event;

    private Map<String, String> paramValues;

    private ReceiverDto receiverDto;

    /**
     * Instantiates a new notification trigger.
     */
    public NotificationTrigger() {
        super();
    }

    /**
     * Instantiates a new notification trigger.
     *
     * @param event
     *            the event
     */
    public NotificationTrigger(EventEnum event) {
        this.event = event;
    }

    /**
     * @return the event
     */
    public EventEnum getEvent() {
        return event;
    }

    /**
     * @return the paramValues
     */
    public Map<String, String> getParamValues() {
        return paramValues;
    }

    /**
     * @return the receiverDto
     */
    public ReceiverDto getReceiverDto() {
        return receiverDto;
    }

    /**
     * @param event
     *            the event to set
     */
    public void setEvent(EventEnum event) {
        this.event = event;
    }

    /**
     * @param paramValues
     *            the paramValues to set
     */
    public void setParamValues(Map<String, String> paramValues) {
        this.paramValues = paramValues;
    }

    /**
     * @param receiverDto
     *            the receiverDto to set
     */
    public void setReceiverDto(ReceiverDto receiverDto) {
        this.receiverDto = receiverDto;
    }

}
