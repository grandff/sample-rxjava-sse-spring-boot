package com.kjm.sample.rxjava.rxjavarestapi.member.model.dto;

import lombok.Data;

@Data
public class UpdateMemberRequestDto {
    private String id;
    private String password;
}
