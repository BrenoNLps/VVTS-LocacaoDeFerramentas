package br.ifsp.demo.domain.exception;

public class InvalidPeriodException extends RuntimeException {
    public InvalidPeriodException() {
        super("Rental period must be at least one day and end date must be after start date");
    }
}
