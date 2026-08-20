package com.cleverson.help_desk.service.application.exceptions;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException() {
        super("Service not found");
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }
}