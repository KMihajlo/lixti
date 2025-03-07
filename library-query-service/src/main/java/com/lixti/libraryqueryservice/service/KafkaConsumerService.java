package com.lixti.libraryqueryservice.service;

import com.lixti.libraryqueryservice.dto.BookEvent;
import com.lixti.libraryqueryservice.dto.BookResponse;
import com.lixti.libraryqueryservice.model.BookQueryModel;
import com.lixti.libraryqueryservice.repository.BookQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Service used to consume/listen for events sent onto the Kafka broker in order to keep the
 * database, from this service, up to date.
 */
@Component
@EnableKafka
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final BookQueryRepository bookQueryRepository;
    private final BookQueryService bookQueryService;

    @KafkaListener(topics = "book-event-topic", groupId = "book-event-group", containerFactory = "factory")
    public void processBookEvents(BookEvent bookEvent) {
        BookResponse bookEventResponse = bookEvent.getBook();

        if (bookEventResponse != null) {
            switch (bookEvent.getEventType()) {
                case "CreateBook":
                    BookQueryModel bookQueryModelCreateBook = bookQueryService.mapToBookQueryModel(bookEventResponse);
                    bookQueryRepository.save(bookQueryModelCreateBook);
                    break;
                case "UpdateBook":
                    BookResponse bookResponseUpdateBook = bookQueryService.getBookById(bookEvent.getBook().getId());
                    bookResponseUpdateBook.setIsbn(bookEventResponse.getIsbn());
                    bookResponseUpdateBook.setTitle(bookEventResponse.getTitle());
                    bookResponseUpdateBook.setAuthor(bookEventResponse.getAuthor());
                    bookResponseUpdateBook.setPublisher(bookEventResponse.getPublisher());
                    bookResponseUpdateBook.setPublishedDate(bookEventResponse.getPublishedDate());
                    bookResponseUpdateBook.setPages(bookEventResponse.getPages());
                    bookResponseUpdateBook.setType(bookEventResponse.getType());

                    BookQueryModel bookQueryModelUpdateBook = bookQueryService.mapToBookQueryModel(bookResponseUpdateBook);
                    bookQueryRepository.save(bookQueryModelUpdateBook);
                    break;
                case "PartialUpdateBook":
                    BookResponse bookResponsePartialUpdateBook = bookQueryService.getBookById(bookEventResponse.getId());
                    bookResponsePartialUpdateBook.setIsbn(StringUtils.isNotBlank(bookEventResponse.getIsbn()) ? bookEventResponse.getIsbn() : bookResponsePartialUpdateBook.getIsbn());
                    bookResponsePartialUpdateBook.setTitle(StringUtils.isNotBlank(bookEventResponse.getTitle()) ? bookEventResponse.getTitle() : bookResponsePartialUpdateBook.getTitle());
                    bookResponsePartialUpdateBook.setAuthor(StringUtils.isNotBlank(bookEventResponse.getAuthor()) ? bookEventResponse.getAuthor() : bookResponsePartialUpdateBook.getAuthor());
                    bookResponsePartialUpdateBook.setPublisher(StringUtils.isNotBlank(bookEventResponse.getPublisher()) ? bookEventResponse.getPublisher() : bookResponsePartialUpdateBook.getPublisher());
                    bookResponsePartialUpdateBook.setPublishedDate(StringUtils.isNotBlank(bookEventResponse.getPublishedDate()) ? bookEventResponse.getPublishedDate() : bookResponsePartialUpdateBook.getPublishedDate());
                    bookResponsePartialUpdateBook.setPages(bookEventResponse.getPages() != 0 ? bookEventResponse.getPages() : bookResponsePartialUpdateBook.getPages());
                    bookResponsePartialUpdateBook.setType(StringUtils.isNotBlank(bookEventResponse.getType().getValue()) ? bookEventResponse.getType() : bookResponsePartialUpdateBook.getType());

                    BookQueryModel bookQueryModelPartialUpdateBook = bookQueryService.mapToBookQueryModel(bookResponsePartialUpdateBook);
                    bookQueryRepository.save(bookQueryModelPartialUpdateBook);
                    break;
                default:
                    log.debug("The processed event type is not being consumed: {}", bookEvent.getEventType());
                    break;
            }
        }
    }
}
