package com.cleverson.help_desk.user.application.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
