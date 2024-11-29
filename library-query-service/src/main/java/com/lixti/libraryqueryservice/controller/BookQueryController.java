package com.lixti.libraryqueryservice.controller;

import com.lixti.libraryqueryservice.dto.BookResponse;
import com.lixti.libraryqueryservice.service.BookQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookQueryController {

    private final BookQueryService bookQueryService;

    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> fetchAll() {
        return new ResponseEntity<>(bookQueryService.getBooks(), HttpStatus.OK);
    }
}
