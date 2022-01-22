package com.example.engineer.exceptions;

public class UserNotFoundException extends GenericException {

    public UserNotFoundException() {
        super("User has not been found");
    }
}
