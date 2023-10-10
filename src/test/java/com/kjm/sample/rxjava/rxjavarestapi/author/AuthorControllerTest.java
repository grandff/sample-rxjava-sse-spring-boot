package com.kjm.sample.rxjava.rxjavarestapi.author;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjm.sample.rxjava.rxjavarestapi.author.model.dto.AddAuthorRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.author.service.AuthorService;

import io.reactivex.rxjava3.core.Single;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    @Test
    public void AddAuthorSuccessReturn201() throws Exception {
        when(authorService.addAuthor(any(AddAuthorRequestDto.class))).thenReturn(Single.just("1"));

        AddAuthorRequestDto requestDto = new AddAuthorRequestDto();
        requestDto.setName("doe");

        // test
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/author/v1.0")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());            
    }
}
