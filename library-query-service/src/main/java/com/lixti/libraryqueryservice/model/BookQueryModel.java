package com.lixti.libraryqueryservice.model;

import com.lixti.libraryqueryservice.enums.BookType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_query")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter @Getter
public class BookQueryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String isbn;

    private String title;

    private String author;

    private String publisher;

    private String publishedDate;

    private int pages;

    @Enumerated(EnumType.STRING)
    private BookType type;
}
