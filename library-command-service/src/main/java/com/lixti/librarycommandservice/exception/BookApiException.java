package com.lixti.librarycommandservice.exception;

public abstract class BookApiException extends RuntimeException {
    protected BookApiException(String message) {
        super(message);
    }

    protected BookApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
