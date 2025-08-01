package com.freddyzhounyc.library_REST_API.services;

import com.freddyzhounyc.library_REST_API.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity createUpdateBook(String isbn, BookEntity book);
    List<BookEntity> findAll();
    Optional<BookEntity> findOne(String isbn);
    boolean isExists(String isbn);
    BookEntity partialUpdate(String isbn, BookEntity bookEntity);
    void delete(String isbn);
}
