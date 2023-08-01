package com.kenzie.dynamodbscan.icecream.exception;

public class RecipeNotFoundException extends Exception {
    private static final long serialVersionUID = -1816675786064688643L;

    public RecipeNotFoundException(String message) {
        super(message);
    }
}
