package com.freddyzhounyc.library_REST_API.services.impl;

import com.freddyzhounyc.library_REST_API.domain.entities.BookEntity;
import com.freddyzhounyc.library_REST_API.repositories.BookRepository;
import com.freddyzhounyc.library_REST_API.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

}
