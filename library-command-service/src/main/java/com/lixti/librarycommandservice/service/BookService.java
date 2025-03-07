package com.lixti.librarycommandservice.service;

import com.lixti.librarycommandservice.dto.BookRequest;
import com.lixti.librarycommandservice.dto.BookResponse;
import com.lixti.librarycommandservice.exception.BookApiBadRequestException;
import com.lixti.librarycommandservice.exception.BookApiResourceNotFoundException;
import com.lixti.librarycommandservice.exception.BookApiServerErrorException;
import com.lixti.librarycommandservice.model.BookModel;
import com.lixti.librarycommandservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final KafkaProducerService kafkaProducerService;

    public BookResponse createBook(BookRequest bookRequest) {
        if (bookRequest == null) {
            throw new BookApiBadRequestException("Book request object is null");
        }

        BookModel bookModel = BookModel.builder()
                                       .isbn(bookRequest.getIsbn())
                                       .title(bookRequest.getTitle())
                                       .author(bookRequest.getAuthor())
                                       .publisher(bookRequest.getPublisher())
                                       .publishedDate(bookRequest.getPublishedDate())
                                       .pages(bookRequest.getPages())
                                       .type(bookRequest.getType())
                                       .build();

        BookResponse bookResponse = save(bookModel);
        kafkaProducerService.sendKafkaEvent(bookResponse, "CreateBook");

        log.info("Book created: {}", bookResponse);
        return bookResponse;
    }

    public BookResponse updateBook(BookRequest bookRequest, String id) {
        return updateBook(bookRequest, id, false);
    }

    public BookResponse partiallyUpdateBook(BookRequest bookRequest, String id) {
        return updateBook(bookRequest, id, true);
    }

    private BookResponse updateBook(BookRequest bookRequest, String id, boolean partialUpdate) {
        if (bookRequest == null || StringUtils.isBlank(id)) {
            log.error("Book object is null or id is empty");
            throw new BookApiBadRequestException("Book object is null or id is empty",
                                                 new IllegalArgumentException());
        }

        Optional<BookModel> bookModelOptional = bookRepository.findById(id);
        if(bookModelOptional.isEmpty()) {
            log.error("Book with id {} not found", id);
            throw new BookApiResourceNotFoundException("Book couldn't be updated, book with id = " + id + " not found");
        }

        BookModel bookModel = bookModelOptional.get();
        if (partialUpdate) {
            bookModel.setIsbn(StringUtils.isNotBlank(bookRequest.getIsbn()) ? bookRequest.getIsbn() : bookModel.getIsbn());
            bookModel.setTitle(StringUtils.isNotBlank(bookRequest.getTitle()) ? bookRequest.getTitle() : bookModel.getTitle());
            bookModel.setAuthor(StringUtils.isNotBlank(bookRequest.getAuthor()) ? bookRequest.getAuthor() : bookModel.getAuthor());
            bookModel.setPublisher(StringUtils.isNotBlank(bookRequest.getPublisher()) ? bookRequest.getPublisher() : bookModel.getPublisher());
            bookModel.setPublishedDate(StringUtils.isNotBlank(bookRequest.getPublishedDate()) ? bookRequest.getPublishedDate() : bookModel.getPublishedDate());
            bookModel.setPages(bookRequest.getPages() != 0 ? bookRequest.getPages() : bookModel.getPages());
            bookModel.setType(StringUtils.isNotBlank(bookRequest.getType().getValue()) ? bookRequest.getType() : bookModel.getType());
        } else {
            bookModel.setIsbn(bookRequest.getIsbn());
            bookModel.setTitle(bookRequest.getTitle());
            bookModel.setAuthor(bookRequest.getAuthor());
            bookModel.setPublisher(bookRequest.getPublisher());
            bookModel.setPublishedDate(bookRequest.getPublishedDate());
            bookModel.setPages(bookRequest.getPages());
            bookModel.setType(bookRequest.getType());
        }

        BookResponse bookResponse = save(bookModel);
        if (partialUpdate) {
            kafkaProducerService.sendKafkaEvent(bookResponse, "PartialUpdateBook");
        } else {
            kafkaProducerService.sendKafkaEvent(bookResponse, "UpdateBook");
        }

        log.info("Book resource updated: {}", bookResponse);
        return bookResponse;
    }

    private BookResponse save(BookModel bookModel) {
        try {
            BookResponse bookResponse = mapToBookResponse(bookRepository.save(bookModel));
            if (bookResponse == null) {
                log.error("Book response is null");
                throw new BookApiServerErrorException("Failed to save book, book response is null");
            }
            return bookResponse;
        } catch (Exception e) {
            log.error("Book object {} couldn't be updated", bookModel);
            throw new BookApiServerErrorException("Failed to save book", e);
        }
    }

    private BookResponse mapToBookResponse(BookModel bookModel) {
        return BookResponse.builder()
                           .id(bookModel.getId())
                           .isbn(bookModel.getIsbn())
                           .title(bookModel.getTitle())
                           .author(bookModel.getAuthor())
                           .publisher(bookModel.getPublisher())
                           .publishedDate(bookModel.getPublishedDate())
                           .pages(bookModel.getPages())
                           .type(bookModel.getType())
                           .build();
    }
}
