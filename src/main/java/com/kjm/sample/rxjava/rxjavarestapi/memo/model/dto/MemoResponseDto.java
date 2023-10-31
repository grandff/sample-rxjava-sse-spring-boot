package com.kjm.sample.rxjava.rxjavarestapi.memo.model.dto;

import lombok.Data;

@Data
public class MemoResponseDto {
    private String frstRegDt;
    private String lstChgDt;
    private String memoTtl;
    private String memoCtt;
}
