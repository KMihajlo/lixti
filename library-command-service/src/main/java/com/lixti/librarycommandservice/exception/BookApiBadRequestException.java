package com.lixti.librarycommandservice.exception;

public class BookApiBadRequestException extends BookApiException {
    public BookApiBadRequestException(String message) {
        super(message);
    }

    public BookApiBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
