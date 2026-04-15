package br.ifsp.demo.exception;


import br.ifsp.demo.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler {
    private ResponseEntity<?> buildResponse(Exception e, HttpStatus status) {
        final ApiException apiException = ApiException.builder()
                .status(status)
                .message(e.getMessage())
                .developerMessage(e.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e){
        return buildResponse(e, BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
        return buildResponse(e, BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e){
        return buildResponse(e, FORBIDDEN);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e){
        return buildResponse(e, NOT_FOUND);
    }

    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleEntityAlreadyExistsException(EntityAlreadyExistsException e){
        return buildResponse(e, CONFLICT);
    }

}
