package com.freddyzhounyc.library_REST_API.controllers;

import com.freddyzhounyc.library_REST_API.domain.dto.AuthorDto;
import com.freddyzhounyc.library_REST_API.domain.entities.AuthorEntity;
import com.freddyzhounyc.library_REST_API.mappers.Mapper;
import com.freddyzhounyc.library_REST_API.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    // ** CHANCE TO USE PAGINATION LATER ON **
    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors() {
        List<AuthorEntity> result = authorService.findAll();
        return result.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

}
