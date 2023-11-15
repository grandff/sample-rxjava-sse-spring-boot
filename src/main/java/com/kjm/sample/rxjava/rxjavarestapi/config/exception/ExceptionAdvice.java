package com.kjm.sample.rxjava.rxjavarestapi.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kjm.sample.rxjava.rxjavarestapi.common.BaseResponse;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.ResultCodeEnum;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.StatusEnum;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, final EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.builder()
                        .status(StatusEnum.FAIL)
                        .data(null)
                        .resultCode(ResultCodeEnum.NOT_FOUND)
                        .resultMsg("정보를 찾을 수 없습니다.")
                        .build());
    }
    
    
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, final EntityExistsException e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(BaseResponse.builder()
                        .status(StatusEnum.FAIL)
                        .data(null)
                        .resultCode(ResultCodeEnum.UNPROCESSABLE_ENTITY)
                        .resultMsg("해당 데이터는 이미 존재합니다.")
                        .build());
    }

}
