package com.cleverson.help_desk.technician.application.exceptions;

public class NoTechnicianAvailableException extends RuntimeException {
    public NoTechnicianAvailableException(String message) {
        super(message);
    }

    public NoTechnicianAvailableException() {
        super("No technicians available");
    }
}
