package com.freddyzhounyc.library_REST_API.services.impl;

import com.freddyzhounyc.library_REST_API.domain.entities.AuthorEntity;
import com.freddyzhounyc.library_REST_API.repositories.AuthorRepository;
import com.freddyzhounyc.library_REST_API.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

}
