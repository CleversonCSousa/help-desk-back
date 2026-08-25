package com.cleverson.help_desk.customer.application.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException{
    public CustomerAlreadyExistsException() {
        super("Customer already exists");
    }

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
