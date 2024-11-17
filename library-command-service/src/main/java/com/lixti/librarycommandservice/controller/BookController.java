package com.lixti.librarycommandservice.controller;

import com.lixti.librarycommandservice.dto.BookRequest;
import com.lixti.librarycommandservice.dto.BookResponse;
import com.lixti.librarycommandservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<BookResponse> create(@RequestBody BookRequest bookRequest) {
        return new ResponseEntity<>(bookService.createBook(bookRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookResponse> update(@RequestBody BookRequest bookRequest, @PathVariable String id) {
        return new ResponseEntity<>(bookService.updateBook(bookRequest, id), HttpStatus.OK);
    }
}
