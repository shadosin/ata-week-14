package com.kenzie.dynamodbscan.icecream.exception;

public class SundaeSerializationException extends RuntimeException {
    private static final long serialVersionUID = 7569877243054721465L;

    public SundaeSerializationException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
