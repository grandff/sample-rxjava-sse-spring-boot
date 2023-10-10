package com.kjm.sample.rxjava.rxjavarestapi.author.controller;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjm.sample.rxjava.rxjavarestapi.author.model.dto.AddAuthorRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.author.service.AuthorService;
import com.kjm.sample.rxjava.rxjavarestapi.common.BaseResponse;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
public class AuthorController {
        private final AuthorService authorService;

        @Operation(summary = "author 추가", description = "author 추가")
        @Parameters({
                        @Parameter(name = "name", description = "이름"),
        })
        @PostMapping(value = "/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public Single<ResponseEntity<BaseResponse<?>>> addAuthor(@RequestBody AddAuthorRequestDto addAuthorRequestDto) {
                return authorService.addAuthor(addAuthorRequestDto)
                                .subscribeOn(Schedulers.io())
                                .map(s -> ResponseEntity
                                                .created(URI.create("/api/author/" + s))
                                                .body(BaseResponse.actionSuccessWithNoContent()));
        }
}
