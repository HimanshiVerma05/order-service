package com.assignment.microservices.order.enums;

public enum RequestorTypeEnum {
    TRAVEL_AGENT, LY_SALES_DIRECT, LY_SALES_FOR_AGENCY;

    public static boolean contains(String str) {
        for (RequestorTypeEnum typeEnum : RequestorTypeEnum.values()) {
            if (str.equals(typeEnum.toString())) {
                return true;
            }
        }
        return false;
    }

}
