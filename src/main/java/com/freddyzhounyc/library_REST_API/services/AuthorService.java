package com.freddyzhounyc.library_REST_API.services;

import com.freddyzhounyc.library_REST_API.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);
    List<AuthorEntity> findAll();
}
