package com.cleverson.help_desk.ticket.application.exceptions;

public class AdditionalServiceNotFoundException extends RuntimeException {
    public AdditionalServiceNotFoundException(String message) {
        super(message);
    }

    public AdditionalServiceNotFoundException() {
        super("Additional service not found");
    }
}
