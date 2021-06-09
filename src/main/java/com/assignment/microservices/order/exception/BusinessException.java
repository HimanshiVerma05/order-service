package com.assignment.microservices.order.exception;

import java.util.List;

import com.assignment.microservices.order.response.ResponseMessage;

/**
 * This class extends the base class {@link NGMBaseException} for providing the framework for checked exception
 * handling. The application should throw this exception in case of business fault.
 */
public class BusinessException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1393115699347678628L;

    /**
     * Instantiates a new GTM business exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param errorCode
     *            The error code is used to identify type of exception ie. may be used to show error on UI
     */
    public BusinessException(final String message, final Throwable cause, final String errorCode) {
        //super(message, cause, errorCode);
    }

    /**
     * Instantiates a new GTM business exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new GTM business exception.
     *
     * @param message
     *            the message
     */
    public BusinessException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new GTM business exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param errorCode
     *            The error code is used to identify type of exception ie. may be used to show error on UI
     * @param isWarning
     *            Exception level to be logged as warning
     */
    public BusinessException(final String message, final Throwable cause, final String errorCode,
        final boolean isWarning) {
       // super(message, cause, errorCode, isWarning);
    }

    /**
     * Instantiates a new GTM business exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param errorCode
     *            The error code is used to identify type of exception ie. may be used to show error on UI
     * @param messages
     *            the response messages
     */
    public BusinessException(final String message, final Throwable cause, final String errorCode,
        final List<ResponseMessage> messages) {
    	
    	//LOGGER.info("error error erro ");
    	System.out.println("error error erro ");
        //super(message, cause, errorCode, messages);
    }
}
