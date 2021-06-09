package com.assignment.microservices.messaging;

/**
 * The Class MessagingConstants for managing messaging constants.
 */
public class MessagingConstants {

    /** The Constant DEFAULT_START_CONS_INTERVAL in millseconds for minimum interval to spawn a new consumer thread. */
    public static final String DEFAULT_START_CONS_INTERVAL = "1000";

    /** The Constant DEFAULT_START_CONS_INTERVAL in millseconds for minimum interval to release a consumer thread. */
    public static final String DEFAULT_STOP_CONS_INTERVAL = "10000";

    /** The Constant DEFAULT_RETRY_ATTEMPT for max retry before move the message to dead_letter_queue **/
    public static final String DEFAULT_RETRY_ATTEMPT = "3";

    /** The Constant DEFAULT_RETRY_DELAY for delay between retry attempts **/
    public static final String DEFAULT_RETRY_DELAY = "1000";

    private MessagingConstants() {
        // Illegal to instantiate
    }

}
