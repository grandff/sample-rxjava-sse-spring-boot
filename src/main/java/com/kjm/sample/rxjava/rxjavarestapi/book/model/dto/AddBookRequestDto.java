package com.kjm.sample.rxjava.rxjavarestapi.book.model.dto;

import lombok.Data;

@Data
public class AddBookRequestDto {
    private String title;
    private String authorId;
}
