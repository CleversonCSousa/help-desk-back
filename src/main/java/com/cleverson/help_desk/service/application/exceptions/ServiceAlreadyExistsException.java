package com.cleverson.help_desk.service.application.exceptions;

public class ServiceAlreadyExistsException extends RuntimeException {
    public ServiceAlreadyExistsException() {
        super("Service already exists");
    }

    public ServiceAlreadyExistsException(String message) {
        super(message);
    }
}
