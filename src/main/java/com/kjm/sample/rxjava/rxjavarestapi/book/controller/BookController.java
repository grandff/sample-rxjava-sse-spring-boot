package com.kjm.sample.rxjava.rxjavarestapi.book.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.AddBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.BookResponseDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.UpdateBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.service.BookService;
import com.kjm.sample.rxjava.rxjavarestapi.common.BaseResponse;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.ResultCodeEnum;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.StatusEnum;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
        private final BookService bookService;

        @Operation(summary = "book 추가", description = "book 추가")
        @Parameters({
                        @Parameter(name = "title", description = "책 이름"),
                        @Parameter(name = "authorId", description = "작가 ID"),
        })
        @PostMapping(value = "/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public Single<ResponseEntity<BaseResponse<?>>> addBook(@RequestBody AddBookRequestDto addBookRequestDto) {
                return bookService.addBook(addBookRequestDto).subscribeOn(Schedulers.io()).map(
                                s -> ResponseEntity.created(URI.create("/api/book/" + s))
                                                .body(BaseResponse.actionCreateSuccess()));
        }

        @Operation(summary = "book 수정", description = "book 수정")
        @Parameters({
                        @Parameter(name = "title", description = "책 이름"),
                        @Parameter(name = "id", description = "책 ID"),
        })
        @PatchMapping(value = "/v1.0/{bookId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public Single<ResponseEntity<BaseResponse<?>>> updateBook(
                        @RequestBody UpdateBookRequestDto updateBookRequestDto,
                        @PathVariable(value = "bookId") String bookId) {
                return bookService.updateBook(updateBookRequestDto).subscribeOn(Schedulers.io())
                                .toSingle(() -> ResponseEntity.ok(BaseResponse.actionSuccess()));
        }

        @Operation(summary = "book 목록", description = "book 목록 조회")
        @Parameters({
                        @Parameter(name = "limit", description = "페이지당 수"),
                        @Parameter(name = "page", description = "페이지번호"),
        })
        @GetMapping(value = "/v1.0", produces = MediaType.APPLICATION_JSON_VALUE)
        public Single<ResponseEntity<BaseResponse<?>>> getAllBooks(
                        @RequestParam(value = "limit", defaultValue = "5") int limit,
                        @RequestParam(value = "page", defaultValue = "0") int page) {

                return bookService.getAllBooks(limit, page)
                                .subscribeOn(Schedulers.io())
                                .map(response -> ResponseEntity.ok(BaseResponse.<List<BookResponseDto>>builder()
                                                .status(StatusEnum.SUCCESS)
                                                .data(response)
                                                .resultCode(ResultCodeEnum.SUCCESS)
                                                .resultMsg("Success")
                                                .build()));
        }

        @Operation(summary = "book 상세조회", description = "book 상세 조회")
        @Parameters({
                        @Parameter(name = "bookId", description = "책 ID"),
        })
        @GetMapping(value = "/v1.0/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public Single<ResponseEntity<BaseResponse<?>>> getBookDetail(@PathVariable(value = "bookId") String bookId) {
                return bookService.getBookDetail(bookId)
                                .subscribeOn(Schedulers.io())
                                .map(response -> ResponseEntity.ok(BaseResponse.<BookResponseDto>builder()
                                                .status(StatusEnum.SUCCESS)
                                                .data(response)
                                                .resultCode(ResultCodeEnum.SUCCESS)
                                                .resultMsg("Success")
                                                .build()));
        }

        @Operation(summary = "book 삭제", description = "book 삭제")
        @Parameters({
                        @Parameter(name = "bookId", description = "책 ID"),
        })
        @DeleteMapping(value = "/v1.0/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public Single<ResponseEntity<BaseResponse<?>>> deleteBOok(@PathVariable(value = "bookId") String bookId) {
                return bookService.deleteBook(bookId)
                                .subscribeOn(Schedulers.io())
                                .toSingle(() -> ResponseEntity.ok(BaseResponse.actionSuccessWithNoContent()));
        }
}
