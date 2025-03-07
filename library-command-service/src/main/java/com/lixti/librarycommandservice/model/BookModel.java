package com.lixti.librarycommandservice.model;

import com.lixti.librarycommandservice.enums.BookType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "book")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter @Getter
public class BookModel {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private String id;

    private String isbn;

    private String title;

    private String author;

    private String publisher;

    private String publishedDate;

    private int pages;

    private BookType type;
}
