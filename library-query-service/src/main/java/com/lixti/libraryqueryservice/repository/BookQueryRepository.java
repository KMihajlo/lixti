package com.lixti.libraryqueryservice.repository;

import com.lixti.libraryqueryservice.model.BookQueryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BookQueryRepository extends JpaRepository<BookQueryModel, String> {
}
