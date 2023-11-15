package com.kjm.sample.rxjava.rxjavarestapi.book;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

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
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.BookResponseDto;
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
        // author 정보가 없음을 가정하기 위해 404 not found exception 발생
        when(bookService.addBook(any(AddBookRequestDto.class)))
                .thenReturn(Single.error(new EntityNotFoundException()));

        // 테스트 시작
        MvcResult mvcResult = mockMvc.perform(post("/api/book/v1.0")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new AddBookRequestDto())))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.status").value(StatusEnum.FAIL.toString()))
            .andExpect(jsonPath("$.data").doesNotExist()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.NOT_FOUND.name()));
        
        // 한번만 호출됐는지 확인
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
    public void UpdateBook_Failed_BookIdNotFound_Return404EntityNotFound() throws JsonProcessingException, Exception {
        // 업데이트 할 책 정보가 없음을 가정하기 위해 404 not found exception 발생
        when(bookService.updateBook(any(UpdateBookRequestDto.class)))
            .thenReturn(Completable.error(new EntityNotFoundException()));

        // 테스트
        MvcResult mvcResult = mockMvc.perform(
                patch("/api/book/v1.0/id", "123")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new UpdateBookRequestDto()))        
            ).andReturn();
        
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(StatusEnum.FAIL.toString()))
            .andExpect(jsonPath("$.data").doesNotExist()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.NOT_FOUND.name()));

        // 한번만 호출했는지 확인
        verify(bookService, times(1)).updateBook(any(UpdateBookRequestDto.class));
    }

    @Test
    public void GetAllBooks_LimitAndPageSpecified_Success_Return200WithListOfBookWebResponse() throws Exception {
        // 책 목록 조회를 위해 기본 값 설정
        when(bookService.getAllBooks(anyInt(), anyInt()))
            .thenReturn(Single.just(Collections.singletonList(new BookResponseDto())));

        // 테스트
        MvcResult mvcResult = mockMvc.perform(
                get("/api/book/v1.0?limit=10&page=0")                
                .contentType(MediaType.APPLICATION_JSON_VALUE)                
            ).andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(StatusEnum.SUCCESS.toString()))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.SUCCESS.name()));

        // 한번만 호출했는지 확인
        verify(bookService, times(1)).getAllBooks(anyInt(), anyInt());
    }

    @Test
    public void GetAllBooks_LimitAndPageNotSpecified_Success_Return200WithListOfBookWebResponse() throws Exception {
        // 책 목록 조회를 위해 기본 값 설정
        when(bookService.getAllBooks(anyInt(), anyInt()))
            .thenReturn(Single.just(Collections.singletonList(new BookResponseDto())));

        // 테스트
        MvcResult mvcResult = mockMvc.perform(
                get("/api/book/v1.0")
                .contentType(MediaType.APPLICATION_JSON_VALUE)                
            ).andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(StatusEnum.SUCCESS.toString()))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.SUCCESS.name()));

        // 한번만 호출했는지 확인
        verify(bookService, times(1)).getAllBooks(anyInt(), anyInt());
    }

    @Test
    public void GetBookDetail_Success_Return200WithBookWebResponse() throws Exception {
        // 상세조회 설정
        when(bookService.getBookDetail(anyString()))
            .thenReturn(Single.just(new BookResponseDto()));

        // 테스트
        MvcResult mvcResult = mockMvc.perform(
                get("/api/book/v1.0/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)                
            ).andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(StatusEnum.SUCCESS.toString()))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.SUCCESS.name()));
        
        // 한번만 호출했는지 확인
        verify(bookService, times(1)).getBookDetail(anyString());
    }

    @Test
    public void GetBookDetail_Failed_BookIdNotFound_Return404EntityNotFound() throws Exception {        
        // not found error setting
        when(bookService.getBookDetail(anyString()))
            .thenReturn(Single.error(new EntityNotFoundException()));

        // 테스트
        MvcResult mvcResult = mockMvc.perform(
                get("/api/book/v1.0/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)                
            ).andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(StatusEnum.FAIL.toString()))
            .andExpect(jsonPath("$.data").doesNotExist()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.NOT_FOUND.name()));

        // 한번만 호출했는지 확인
        verify(bookService, times(1)).getBookDetail(anyString());
    }

    @Test
    public void DeleteBook_Success_Return200() throws Exception {
        // 삭제설정
        when(bookService.deleteBook(anyString()))
            .thenReturn(Completable.complete());
        
        // 테스트
        MvcResult mvcResult = mockMvc.perform(
            delete("/api/book/v1.0/1")   
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(StatusEnum.SUCCESS.toString()))
            .andExpect(jsonPath("$.data").doesNotExist()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.SUCCESS_DELETE.name()));

        // 한번만 호출했는지 확인
        verify(bookService, times(1)).deleteBook(anyString());
    }

    @Test
    public void DeleteBook_Failed_BookIdNotFound_Return404EntityNotFound() throws Exception {
        // not found 설정
        when(bookService.deleteBook(anyString()))
            .thenReturn(Completable.error(new EntityNotFoundException()));
        
        // 테스트
        MvcResult mvcResult = mockMvc.perform(
            delete("/api/book/v1.0/1")
        ).andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(StatusEnum.FAIL.toString()))
            .andExpect(jsonPath("$.data").doesNotExist()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.NOT_FOUND.name()));
        
        // 한번만 호출했는지 확인
        verify(bookService, times(1)).deleteBook(anyString());
    }
}
