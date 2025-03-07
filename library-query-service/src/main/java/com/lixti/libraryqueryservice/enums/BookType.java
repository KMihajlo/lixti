package com.lixti.libraryqueryservice.enums;

import lombok.Getter;

public enum BookType {
    PAPERBACK("Paperback"),
    HARDCOVER("Hardcover"),
    EBOOK("E-book"),
    AUDIOBOOK("Audiobook");

    @Getter
    private final String value;

    BookType(String bookType) {
        this.value = bookType;
    }
}
