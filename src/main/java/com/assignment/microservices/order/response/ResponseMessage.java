package com.assignment.microservices.order.response;


import java.io.Serializable;

import com.assignment.microservices.order.enums.MessageSeverity;

public class ResponseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String identifier;	// Send JSON Path
    private String code;
    private MessageSeverity severity;

    public ResponseMessage() {
    }

    public ResponseMessage(String code, MessageSeverity severity) {
        this.code = code;
        this.severity = severity;
    }

    public ResponseMessage(String identifier, String code, MessageSeverity severity) {
        this.identifier = identifier;
        this.code = code;
        this.severity = severity;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MessageSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(MessageSeverity severity) {
        this.severity = severity;
    }
}
