package com.kjm.sample.rxjava.rxjavarestapi.common.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResultCodeEnum {
    SUCCESS(200),
    SUCCESS_CREATE(201),
    SUCCESS_DELETE(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    RUNTIME_ERROR(406),
    UNPROCESSABLE_ENTITY(422),
    SERVER_ERROR(500),
    NULL_ERROR(500);

    private int code;

    ResultCodeEnum(int code) {
        this.code = code;
    }

}
