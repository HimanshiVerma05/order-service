package com.assignment.microservices.order.response;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String status = "SUCCESS";
    protected T data;
    protected List<ResponseMessage> messages;

    /** The param map. */
    private Map<String, Object> paramMap;

    public BaseResponse() {

    }

    public BaseResponse(String status, List<ResponseMessage> messages) {
        this.setStatus(status);
        this.setMessages(messages);
    }

    public BaseResponse(String status, T data) {
        this.setStatus(status);
        this.setData(data);
    }
    
    public BaseResponse(T data) {
        this.setData(data);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ResponseMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ResponseMessage> messages) {
        this.messages = messages;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

}
