package com.freddyzhounyc.library_REST_API.repositories;

import com.freddyzhounyc.library_REST_API.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String>, PagingAndSortingRepository<BookEntity, String> {
}
