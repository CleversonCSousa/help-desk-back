package com.cleverson.help_desk.technician.application.exceptions;

public class TechnicianNotFound extends RuntimeException {
    public TechnicianNotFound(String message) {
        super(message);
    }

    public TechnicianNotFound() {
        super("Technician not found");
    }
}
