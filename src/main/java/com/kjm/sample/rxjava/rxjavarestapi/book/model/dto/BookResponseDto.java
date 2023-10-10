package com.kjm.sample.rxjava.rxjavarestapi.book.model.dto;

import lombok.Data;

@Data
public class BookResponseDto {
    private String id;
    private String title;
    private String authorName;
}
