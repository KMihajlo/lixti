package com.lixti.libraryqueryservice.service;

import com.lixti.libraryqueryservice.dto.BookResponse;
import com.lixti.libraryqueryservice.model.BookQueryModel;
import com.lixti.libraryqueryservice.repository.BookQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookQueryService {

    private final BookQueryRepository bookQueryRepository;

    public List<BookResponse> getBooks() {
        List<BookQueryModel> bookQueryModels = bookQueryRepository.findAll();

        List<BookResponse> bookResponses = new ArrayList<>();
        for (BookQueryModel bookQueryModel : bookQueryModels) {
            log.info(bookQueryModel.toString());
            bookResponses.add(mapToBookResponse(bookQueryModel));
        }
        return bookResponses;
    }

    private BookResponse mapToBookResponse(BookQueryModel bookQueryModel) {
        return BookResponse.builder()
                           .id(bookQueryModel.getId())
                           .isbn(bookQueryModel.getIsbn())
                           .title(bookQueryModel.getTitle())
                           .author(bookQueryModel.getAuthor())
                           .publisher(bookQueryModel.getPublisher())
                           .publishedDate(bookQueryModel.getPublishedDate())
                           .pages(bookQueryModel.getPages())
                           .type(bookQueryModel.getType())
                           .build();
    }

}