package com.freddyzhounyc.library_REST_API.controllers;

import com.freddyzhounyc.library_REST_API.TestDataUtil;
import com.freddyzhounyc.library_REST_API.domain.entities.AuthorEntity;
import com.freddyzhounyc.library_REST_API.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

//    @Test
//    public void testThatCreateBookSuccessfullyReturns201Created() {
//        AuthorEntity author = TestDataUtil.createTestAuthorA();
//        BookEntity book = TestDataUtil.createTestBookA(author);
//        mockMvc.perform(
//                MockMvcRequestBuilders.
//        )
//    }

}
