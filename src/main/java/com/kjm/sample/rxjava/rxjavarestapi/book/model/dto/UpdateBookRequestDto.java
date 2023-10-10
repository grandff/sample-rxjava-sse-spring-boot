package com.kjm.sample.rxjava.rxjavarestapi.book.model.dto;

import lombok.Data;

@Data
public class UpdateBookRequestDto {
    private String id;
    private String title;
}
