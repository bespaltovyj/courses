package com.epam.training.task9.rdb.exception;

public class CreationTableException extends Exception {
    public CreationTableException(String message) {
        super(message);
    }

    public CreationTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreationTableException(Throwable cause) {
        super(cause);
    }
}
