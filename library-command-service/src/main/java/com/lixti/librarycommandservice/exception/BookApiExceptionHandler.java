package com.lixti.librarycommandservice.exception;

import com.lixti.librarycommandservice.dto.BookApiExceptionPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class BookApiExceptionHandler {

    @ExceptionHandler(value = {BookApiResourceNotFoundException.class})
    public ResponseEntity<Object> handleBookApiResourceNotFoundException(BookApiResourceNotFoundException exception) {
        return handleBookApiRequestExceptions(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BookApiBadRequestException.class})
    public ResponseEntity<Object> handleBookApiBadRequestException(BookApiBadRequestException exception) {
        return handleBookApiRequestExceptions(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BookApiServerErrorException.class})
    public ResponseEntity<Object> handleBookApiBadRequestException(BookApiServerErrorException exception) {
        return handleBookApiRequestExceptions(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleBookApiRequestExceptions(BookApiException exception, HttpStatus httpStatus) {
        BookApiExceptionPayload bookApiException = new BookApiExceptionPayload(exception.getMessage(),
                                                                               exception,
                                                                               httpStatus,
                                                                               ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(bookApiException, httpStatus);
    }
}
