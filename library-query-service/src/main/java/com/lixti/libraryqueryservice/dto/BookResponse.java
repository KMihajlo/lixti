package com.lixti.libraryqueryservice.dto;

import com.lixti.libraryqueryservice.enums.BookType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private String id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String publishedDate;
    private int pages;
    private BookType type;
}
