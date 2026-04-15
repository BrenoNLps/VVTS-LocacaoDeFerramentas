package br.ifsp.demo.exception;


import br.ifsp.demo.domain.exception.*;
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
    }//

    @ExceptionHandler(value = InvalidArgumentException.class)
    public ResponseEntity<?> handleInvalidArgumentException(InvalidArgumentException e) {
        return buildResponse(e, BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidDateException.class)
    public ResponseEntity<?> handleInvalidDateException(InvalidDateException e) {
        return buildResponse(e, BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidPeriodException.class)
    public ResponseEntity<?> handleInvalidPeriodException(InvalidPeriodException e) {
        return buildResponse(e, BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingGuaranteeException.class)
    public ResponseEntity<?> handleMissingGuaranteeException(MissingGuaranteeException e) {
        return buildResponse(e, BAD_REQUEST);
    }

    @ExceptionHandler(value = ToolUnavailableException.class)
    public ResponseEntity<?> handleToolUnavailableException(ToolUnavailableException e) {
        return buildResponse(e, CONFLICT);
    }

    @ExceptionHandler(value = ToolInUseException.class)
    public ResponseEntity<?> handleToolInUseException(ToolInUseException e) {
        return buildResponse(e, CONFLICT);
    }

    @ExceptionHandler(value = ToolAlreadyInMaintenanceException.class)
    public ResponseEntity<?> handleToolAlreadyInMaintenanceException(ToolAlreadyInMaintenanceException e) {
        return buildResponse(e, CONFLICT);
    }

    @ExceptionHandler(value = InvalidToolStateException.class)
    public ResponseEntity<?> handleInvalidToolStateException(InvalidToolStateException e) {
        return buildResponse(e, CONFLICT);
    }

    @ExceptionHandler(value = RentalAlreadyFinalizedException.class)
    public ResponseEntity<?> handleRentalAlreadyFinalizedException(RentalAlreadyFinalizedException e) {
        return buildResponse(e, CONFLICT);
    }

    @ExceptionHandler(value = RentalAlreadyCancelledException.class)
    public ResponseEntity<?> handleRentalAlreadyCancelledException(RentalAlreadyCancelledException e) {
        return buildResponse(e, CONFLICT);
    }

}
