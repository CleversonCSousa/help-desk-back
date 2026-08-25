package com.cleverson.help_desk.customer.application.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("Customer not found");
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

}
