package com.kjm.sample.rxjava.rxjavarestapi.book.service;

import java.util.List;

import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.AddBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.BookResponseDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.UpdateBookRequestDto;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface BookService {
    Single<String> addBook(AddBookRequestDto addBookRequestDto);

    Completable updateBook(UpdateBookRequestDto updateBookRequestDto);

    Single<List<BookResponseDto>> getAllBooks(int limit, int page);

    Single<BookResponseDto> getBookDetail(String id);

    Completable deleteBook(String id);
}
