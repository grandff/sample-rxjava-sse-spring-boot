package com.kjm.sample.rxjava.rxjavarestapi.book;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.h2.api.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjm.sample.rxjava.rxjavarestapi.author.model.dto.AddAuthorRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.AddBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.UpdateBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.service.BookService;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.ResultCodeEnum;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.StatusEnum;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    // 책 등록 정상 여부 테스트
    @Test
    public void AddBook_Success_Return201() throws JsonProcessingException, Exception  {
        // addBook 메서드가 addBookRequestDto 클래스의 어떤 객체를 인자로 받아도 항상 1로 반환 하도록 설정
        when(bookService.addBook(any(AddBookRequestDto.class)))
        .thenReturn(Single.just("1"));

        // 테스트
        MvcResult mvcResult = mockMvc.perform(post("/api/book/v1.0")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(new AddAuthorRequestDto())))
            .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data").doesNotExist()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value("SUCCESS_CREATE"));
                                        
        // addBook 메서드가 1번 호출 되었는지 검증
        verify(bookService, times(1)).addBook(any(AddBookRequestDto.class));
    }

    @Test
    public void AddBook_Failed_AuthorNotFound_Return404EntityNotFound() throws JsonProcessingException, Exception {
        when(bookService.addBook(any(AddBookRequestDto.class)))
                .thenReturn(Single.error(new EntityNotFoundException()));

        MvcResult mvcResult = mockMvc.perform(post("/api/book/v1.0")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new AddBookRequestDto())))
                .andReturn();

        // mockMvc.perform(asyncDispatch(mvcResult))
        //         .andExpect(status().isNotFound())
        //         .andExpect(jsonPath("$.errorCode", equalTo(ErrorCode.ENTITY_NOT_FOUND.toString())))
        //         .andExpect(jsonPath("$.data", nullValue()));

        verify(bookService, times(1)).addBook(any(AddBookRequestDto.class));

    }

    @Test
    public void UpdateBook_Success_Return200() throws JsonProcessingException, Exception {
        // updateBook 메서드가 UpdateBookRequestDto 클래스의 어떤 객체를 인자로 받아도 Completable 의 complete를 호출하도록 설정
        when(bookService.updateBook(any(UpdateBookRequestDto.class)))
            .thenReturn(Completable.complete());

        // 테스트
        MvcResult mvcResult = mockMvc.perform(
                patch("/api/book/v1.0/id", "123")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new UpdateBookRequestDto()))        
            ).andReturn();
        
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data").doesNotExist()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value("SUCCESS"));

        // updateBook 메서드가 1번만 호출되었는지 검증
        verify(bookService, times(1)).updateBook(any(UpdateBookRequestDto.class));
    }

    @Test
    public void UpdateBook_Failed_BookIdNotFound_Return404EntityNotFound() {

    }

    @Test
    public void GetAllBooks_LimitAndPageSpecified_Success_Return200WithListOfBookWebResponse() {

    }

    @Test
    public void GetAllBooks_LimitAndPageNotSpecified_Success_Return200WithListOfBookWebResponse() {

    }

    @Test
    public void GetBookDetail_Success_Return200WithBookWebResponse() {

    }

    @Test
    public void GetBookDetail_Failed_BookIdNotFound_Return404EntityNotFound() {

    }

    @Test
    public void DeleteBook_Success_Return200() {

    }

    @Test
    public void DeleteBook_Failed_BookIdNotFound_Return404EntityNotFound() {

    }
}
