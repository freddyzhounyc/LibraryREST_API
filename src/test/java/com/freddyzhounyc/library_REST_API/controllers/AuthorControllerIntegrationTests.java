package com.freddyzhounyc.library_REST_API.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freddyzhounyc.library_REST_API.TestDataUtil;
import com.freddyzhounyc.library_REST_API.domain.dto.AuthorDto;
import com.freddyzhounyc.library_REST_API.domain.entities.AuthorEntity;
import com.freddyzhounyc.library_REST_API.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        String json = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        String json = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorA.getAge())
        );
    }
    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorService.save(author);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(author.getAge())
        );
    }
    @Test
    public void testThatFindOneAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorService.save(author);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatFindOneAuthorReturnsHttpStatus404WhenAuthorDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatFindOneAuthorReturnsStoredAuthorWhenAuthorExists() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(author.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(author.getAge())
        );
    }
    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(author);
        AuthorDto updateAuthor = TestDataUtil.createTestAuthorDtoB();
        String json = objectMapper.writeValueAsString(updateAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404WhenAuthorDoesNotExist() throws Exception {
        AuthorDto author = TestDataUtil.createTestAuthorDtoA();
        String json = objectMapper.writeValueAsString(author);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatFullUpdateAuthorUpdatedAuthorAndReturnsUpdatedAuthorWhenAuthorExists() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(author);
        AuthorDto updateAuthor = TestDataUtil.createTestAuthorDtoB();
        updateAuthor.setId(savedAuthor.getId());
        String json = objectMapper.writeValueAsString(updateAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(updateAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(updateAuthor.getAge())
        );
    }
}
