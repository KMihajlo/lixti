package com.lixti.librarycommandservice.exception;

public class BookApiServerErrorException extends BookApiException {
    public BookApiServerErrorException(String message) {
        super(message);
    }

    public BookApiServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
