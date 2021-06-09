package com.assignment.microservices.order.service;

import java.util.Map;

import com.assignment.microservices.order.enums.EventEnum;
import com.assignment.microservices.order.exception.BusinessException;


/**
 * The Interface INotificationService.
 *
 * @author Himanshi Verma
 */
public interface INotificationService {

    /**
     * Sets the and publish notification trigger.
     *
     * @param event
     *            the event
     * @param test
     *            the param map
     * @param pnr
     *            the pnr
     * @throws BusinessException
     *             the  business exception
     */
    

	void setAndPublishNotificationTrigger(EventEnum event, String email) throws BusinessException;

}
