package com.cleverson.help_desk.infrastructure.exceptions;

import com.cleverson.help_desk.user.application.exceptions.InvalidCredentialsException;
import com.cleverson.help_desk.user.application.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<RestErrorMessage> userAlreadyExistsHandler(UserAlreadyExistsException exception) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<RestErrorMessage> badCredentialsHandler(InvalidCredentialsException exception) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
