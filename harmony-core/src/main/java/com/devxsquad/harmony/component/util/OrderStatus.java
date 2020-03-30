package com.devxsquad.harmony.component.util;

public enum OrderStatus {
    TODO, PREPARING, DONE, ONTHEWAY, DELIVERED;

    public static final String ORDER_STATUS_TO_STRING = "TODO|PREPARING|DONE|ONTHEWAY|DELIVERED";

    @Override
    public String toString() {
        return ORDER_STATUS_TO_STRING;
    }
}
