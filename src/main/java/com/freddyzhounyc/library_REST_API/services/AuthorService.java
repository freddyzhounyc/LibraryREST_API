package com.freddyzhounyc.library_REST_API.services;

import com.freddyzhounyc.library_REST_API.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);
    List<AuthorEntity> findAll();
    Optional<AuthorEntity> findOne(Long id);
    boolean isExists(Long id);
}
