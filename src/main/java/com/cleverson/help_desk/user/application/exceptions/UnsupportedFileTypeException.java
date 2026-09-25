package com.cleverson.help_desk.user.application.exceptions;

public class UnsupportedFileTypeException extends RuntimeException {
    public UnsupportedFileTypeException(String message) {
        super(message);
    }

    public UnsupportedFileTypeException() {
        super("Unsupported file type");
    }
}
