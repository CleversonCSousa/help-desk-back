package com.cleverson.help_desk.ticket.application.exceptions;

public class UnauthorizedTicketAccessException extends RuntimeException {
    public UnauthorizedTicketAccessException(String message) {
        super(message);
    }

    public UnauthorizedTicketAccessException() {
        super("Unauthorized ticket access");
    }
}