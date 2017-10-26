package com.epam.training.task7.exception;

public class ValidateDataException extends Exception {

    public ValidateDataException(String message) {
        super(message);
    }

    public ValidateDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
