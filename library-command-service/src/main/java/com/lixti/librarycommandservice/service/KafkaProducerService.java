package com.lixti.librarycommandservice.service;

import com.lixti.librarycommandservice.dto.BookEvent;
import com.lixti.librarycommandservice.dto.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service used to produce/send events into Kafka broker.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, BookEvent> kafkaTemplate;

    public void sendKafkaEvent(BookResponse bookResponse, String eventType) {
        BookEvent event = new BookEvent(eventType, bookResponse);
        kafkaTemplate.send("book-event-topic", event);
        log.info("Kafka event sent: {}", event);
    }
}
