package com.freddyzhounyc.library_REST_API.services;

import com.freddyzhounyc.library_REST_API.domain.entities.BookEntity;

import java.util.List;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);
    List<BookEntity> findAll();
}
