package com.kjm.sample.rxjava.rxjavarestapi.author.service;

import com.kjm.sample.rxjava.rxjavarestapi.author.model.dto.AddAuthorRequestDto;

import io.reactivex.rxjava3.core.Single;

public interface AuthorService {
    Single<String> addAuthor(AddAuthorRequestDto addAuthorRequestDto);
}
