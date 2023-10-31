package com.kjm.sample.rxjava.rxjavarestapi.memo.model.dto;

import lombok.Data;

@Data
public class MemoRequestDto {
    private Long memSeq;
    private String memoTtl;
    private String memoCtt;
}
