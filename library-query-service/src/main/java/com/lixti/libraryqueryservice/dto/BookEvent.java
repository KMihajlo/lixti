package com.lixti.libraryqueryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Book Event - for communication between the services, via Kafka broker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEvent {

    // the type of event we are going to perform. (Create, Update, etc..)
    private String eventType;
    // the object that will be sent to the consumer.
    private BookResponse book;
}
