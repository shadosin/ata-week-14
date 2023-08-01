package com.kenzie.serialization.order;

public class OrderSerializationException extends RuntimeException {

    private static final long serialVersionUID = 3267229649268896101L;

    public OrderSerializationException(String message, Throwable t) {
        super(message, t);
    }
}
