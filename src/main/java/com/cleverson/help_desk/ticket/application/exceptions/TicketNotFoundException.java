package com.cleverson.help_desk.ticket.application.exceptions;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String message) {
        super(message);
    }

    public TicketNotFoundException() {
        super("Ticket not found");
    }
}