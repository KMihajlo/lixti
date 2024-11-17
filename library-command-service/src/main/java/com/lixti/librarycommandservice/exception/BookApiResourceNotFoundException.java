package com.lixti.librarycommandservice.exception;

public class BookApiResourceNotFoundException extends BookApiException {
    public BookApiResourceNotFoundException(String message) {
        super(message);
    }

    public BookApiResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
