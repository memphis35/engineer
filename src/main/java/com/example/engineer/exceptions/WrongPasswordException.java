package com.example.engineer.exceptions;

public class WrongPasswordException extends GenericException {
    public WrongPasswordException(String message) {
        super(message);
    }
}
