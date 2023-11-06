package com.kjm.sample.rxjava.rxjavarestapi.memo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
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
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.BookResponseDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.UpdateBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.service.BookService;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.ResultCodeEnum;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.StatusEnum;
import com.kjm.sample.rxjava.rxjavarestapi.memo.model.MemoVo;
import com.kjm.sample.rxjava.rxjavarestapi.memo.model.dto.MemoRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.memo.service.MemoService;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MemoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemoService memoService;
    
    @MockBean
    private BookService bookService;

    // 메모 등록 정상 여부
    @Test
    public void AddMemo_Success_Return201() throws JsonProcessingException, Exception {
        // 어떤 객체를 받아도 1로 생성하도록 설정        
        when(memoService.addMemo(any(MemoRequestDto.class)))
        .thenReturn(new MemoVo(1L, "1", "1"));

        // 테스트 
        MemoRequestDto requestDto = new MemoRequestDto();
        requestDto.setMemoTtl("1");
        requestDto.setMemoCtt("1");
        MvcResult mvcResult = mockMvc.perform(post("/api/memo/v1.0")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(requestDto)))
            .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data").exists()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value("SUCCESS_CREATE"));
            

        // 중복호출 확인
        verify(memoService, times(1)).addMemo(any(MemoRequestDto.class));
    }

    // 메모 수정 정상 여부
    @Test
    public void UpdateMemo_Success_Return200() throws JsonProcessingException, Exception {
        // 어떤 객체를 받아도 1로 생성하도록 설정        
        when(memoService.updateMemo(any(MemoRequestDto.class)))
        .thenReturn(new MemoVo(1L, "1", "1"));

        // 테스트 
        MemoRequestDto requestDto = new MemoRequestDto();
        requestDto.setMemoTtl("1");
        requestDto.setMemoCtt("1");
        MvcResult mvcResult = mockMvc.perform(
            patch("/api/memo/v1.0/id/123")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(requestDto)))
            .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data").exists()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value("SUCCESS"));
            

        // 중복호출 확인
        verify(memoService, times(1)).updateMemo(any(MemoRequestDto.class));
    }

    // 없는 메모를 설정한 경우 메모 수정 실패 여부
    @Test
    public void UpdateMemo_Fail_NotFound_Return404() throws JsonProcessingException, Exception {
        when(memoService.updateMemo(any(MemoRequestDto.class)))
        .thenReturn(null);

        // 테스트
        MemoRequestDto requestDto = new MemoRequestDto();
        requestDto.setMemoTtl("1");
        requestDto.setMemoCtt("1");
        MvcResult mvcResult = mockMvc.perform(
            patch("/api/memo/v1.0/id", "123")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(requestDto)))
            .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(StatusEnum.FAIL.toString()))
            .andExpect(jsonPath("$.data").doesNotExist()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.NOT_FOUND.name()));

        // 한번만 호출했는지 확인
        verify(memoService, times(1)).updateMemo(any(MemoRequestDto.class));
    }

    // 메모 상세 조회
    @Test
    public void GetMemo_Success_Return200WithMemoVo() throws Exception {
        // 상세조회 설정
        Optional<MemoVo> vo = Optional.of(new MemoVo(1L, "1", "1"));
        when(memoService.getMemo(anyLong()))
                .thenReturn(vo);

        // 테스트
        MvcResult mvcResult = mockMvc.perform(
                get("/api/memo/v1.0/id", "123")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(StatusEnum.SUCCESS.toString()))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.SUCCESS.name()));

        // 한번만 호출했는지 확인
        verify(memoService, times(1)).getMemo(anyLong());
    }

    // 없는 메모를 설정한 경우 메모 상세 조회 실패 여부
    @Test
    public void GetMemo_Fail_NotFound_Return404() throws Exception {
        // not found error setting
        when(memoService.getMemo(anyLong()))
            .thenReturn(null);

        // 테스트
        MvcResult mvcResult = mockMvc.perform(
                get("/api/memo/v1.0/id", "123")
                .contentType(MediaType.APPLICATION_JSON_VALUE)                
            ).andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(StatusEnum.FAIL.toString()))
            .andExpect(jsonPath("$.data").doesNotExist()) // 또는 .value(null)
            .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.NOT_FOUND.name()));

        // 한번만 호출했는지 확인
        verify(memoService, times(1)).getMemo(anyLong());
    }

    // 메모 목록 조회
    @Test
    public void GetMemoList_Success_Return200WithMemoList() throws Exception {
        // 책 목록 조회를 위해 기본 값 설정
        PageRequest pageRequest = PageRequest.of(0, 10);
        MemoVo vo = new MemoVo(1L, "1", "1");
        when(memoService.getMemoList(pageRequest))
                .thenReturn(Collections.singletonList(vo));

        // 테스트
        MvcResult mvcResult = mockMvc.perform(
                get("/api/memo/v1.0")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(StatusEnum.SUCCESS.toString()))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.resultCode").value(ResultCodeEnum.SUCCESS.name()));

        // 한번만 호출했는지 확인
        verify(memoService, times(1)).getAllBooks(anyInt(), anyInt());
    }

    // 특정 제목을 포함하는 메모 목록 조회
    @Test
    public void GetMemoListContainTtl_Success_Return200WithMemoList() throws Exception {
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

    // 메모 삭제
    @Test
    public void DeleteMemo_Success_Return200() throws Exception {
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

    // 특정 제목을 포함하는 메모 삭제
    @Test
    public void DeleteMemoContainTtl_Success_Return200() throws Exception{
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

}
