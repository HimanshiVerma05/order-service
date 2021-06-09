package com.assignment.microservices.order.notification;



/**
 * Receiver Information related DTO.
 *
 * @author Himanshi Verma
 */
public class ReceiverDto {

 



    /** The email. */
    private String email;

    /**
     * Instantiates a new receiver dto.
     */
    public ReceiverDto() {
        super();
    }




   
    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
