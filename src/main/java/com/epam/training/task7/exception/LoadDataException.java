package com.epam.training.task7.exception;

public class LoadDataException extends Exception {

    public LoadDataException(String message) {
        super(message);
    }

    public LoadDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
