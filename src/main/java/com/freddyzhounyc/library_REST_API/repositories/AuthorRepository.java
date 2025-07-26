package com.freddyzhounyc.library_REST_API.repositories;

import com.freddyzhounyc.library_REST_API.domain.entities.AuthorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
}
