package com.freddyzhounyc.library_REST_API.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freddyzhounyc.library_REST_API.TestDataUtil;
import com.freddyzhounyc.library_REST_API.domain.dto.BookDto;
import com.freddyzhounyc.library_REST_API.domain.entities.BookEntity;
import com.freddyzhounyc.library_REST_API.services.BookService;
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
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookSuccessfullyReturns201Created() throws Exception {
        BookDto book = TestDataUtil.createTestBookDtoA(null);
        String json = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception {
        BookDto book = TestDataUtil.createTestBookDtoA(null);
        String json = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
        );
    }
    @Test
    public void testThatListBooksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatListBookReturnsListOfBooks() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(book.getIsbn(), book);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(book.getTitle())
        );
    }
    @Test
    public void testThatFindOneBookReturnsHttpStatus200WhenBookExists() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(book.getIsbn(), book);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatFindOneBookReturnsHttpStatus404WhenBookDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/978-1-2345-6789-0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatFindOneBookReturnsStoredBookWhenBookExists() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(book.getIsbn(), book);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
        );
    }
    @Test
    public void testThatCreateUpdateBookReturnsHttpStatus200WhenBookExists() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(book.getIsbn(), book);
        BookDto updateBook = TestDataUtil.createTestBookDtoB(null);
        updateBook.setIsbn(savedBook.getIsbn());
        String json = objectMapper.writeValueAsString(updateBook);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(book.getIsbn(), book);
        BookDto updateBook = TestDataUtil.createTestBookDtoB(null);
        updateBook.setIsbn(savedBook.getIsbn());
        String json = objectMapper.writeValueAsString(updateBook);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(updateBook.getTitle())
        );
    }
    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus200() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        BookDto updateBook = TestDataUtil.createTestBookDtoB(null);
        updateBook.setIsbn(null);
        String json = objectMapper.writeValueAsString(updateBook);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        BookDto updateBook = TestDataUtil.createTestBookDtoB(null);
        updateBook.setIsbn(null);
        String json = objectMapper.writeValueAsString(updateBook);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(updateBook.getTitle())
        );
    }

}
