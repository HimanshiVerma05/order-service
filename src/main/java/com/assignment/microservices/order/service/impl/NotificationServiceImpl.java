package com.assignment.microservices.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.assignment.microservices.order.enums.EventEnum;

import com.assignment.microservices.order.notification.NotificationTrigger;
import com.assignment.microservices.order.notification.ReceiverDto;
import com.assignment.microservices.order.exception.BusinessException;

import com.assignment.microservices.order.service.INotificationService;

/**
 * The Class NotificationServiceImpl.
 *
 * @author Himanshi verma
 */
@Service
public class NotificationServiceImpl implements INotificationService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void setAndPublishNotificationTrigger(EventEnum event, String email)
        throws BusinessException {
        
      
        
        String params= event.toString() +"-"+email;
       

        LOGGER.info("Event published to queue for processing e-mail for pnr with record locator ");

        applicationContext.publishEvent(params);

    }

   

}
