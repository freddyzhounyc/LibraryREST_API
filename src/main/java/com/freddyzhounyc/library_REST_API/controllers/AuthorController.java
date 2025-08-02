package com.freddyzhounyc.library_REST_API.controllers;

import com.freddyzhounyc.library_REST_API.domain.dto.AuthorDto;
import com.freddyzhounyc.library_REST_API.domain.entities.AuthorEntity;
import com.freddyzhounyc.library_REST_API.mappers.Mapper;
import com.freddyzhounyc.library_REST_API.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
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
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<AuthorEntity> result = authorService.findOne(id);
        return result.map(authorEntity -> {
                    AuthorDto authorDto = authorMapper.mapTo(authorEntity);
                    return new ResponseEntity<>(authorDto, HttpStatus.OK);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if (!authorService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        authorDto.setId(id);
        AuthorEntity savedAuthor = authorService.save(authorMapper.mapFrom(authorDto));
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.OK);
    }
    @PatchMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if (!authorService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        AuthorEntity author = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthor = authorService.partialUpdate(id, author);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.OK);
    }
    @DeleteMapping("/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id) {
        authorService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
