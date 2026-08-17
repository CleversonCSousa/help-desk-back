package com.cleverson.help_desk.user.application.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
        super("User already exists");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
