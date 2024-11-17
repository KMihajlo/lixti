package com.lixti.librarycommandservice.dto;

import com.lixti.librarycommandservice.enums.BookType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
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
