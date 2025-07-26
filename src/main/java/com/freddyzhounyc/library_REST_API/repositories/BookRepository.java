package com.freddyzhounyc.library_REST_API.repositories;

import com.freddyzhounyc.library_REST_API.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
