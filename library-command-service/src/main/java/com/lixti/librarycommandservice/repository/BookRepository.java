package com.lixti.librarycommandservice.repository;

import com.lixti.librarycommandservice.model.BookModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<BookModel, String> {
}
