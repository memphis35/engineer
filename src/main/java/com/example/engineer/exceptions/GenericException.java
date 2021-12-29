package com.example.engineer.exceptions;

public abstract class GenericException extends RuntimeException {

    public GenericException() {
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }
}
