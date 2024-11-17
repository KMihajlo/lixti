package com.lixti.librarycommandservice.dto;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record BookApiExceptionPayload(String message, Throwable throwable, HttpStatus httpStatus,
                                      ZonedDateTime timestamp) {
}
