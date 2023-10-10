package com.kjm.sample.rxjava.rxjavarestapi.common;

import com.kjm.sample.rxjava.rxjavarestapi.common.enums.ResultCodeEnum;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.StatusEnum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data
public class BaseResponse<T> {
    private StatusEnum status;
    private T data;
    private ResultCodeEnum resultCode;
    private String resultMsg;

    // SUCCESS
    public static BaseResponse<?> actionSuccess() {
        return new BaseResponse<>(StatusEnum.SUCCESS, null, ResultCodeEnum.SUCCESS, "정상적으로 처리되었습니다.");
    }

    // INSERT SUCCESS
    public static BaseResponse<?> actionCreateSuccess() {
        return new BaseResponse<>(StatusEnum.SUCCESS, null, ResultCodeEnum.SUCCESS_CREATE, "정상적으로 등록됐습니다.");
    }

    // DELETE SUCCESS
    public static BaseResponse<?> actionSuccessWithNoContent() {
        return new BaseResponse<>(StatusEnum.SUCCESS, null, ResultCodeEnum.SUCCESS_DELETE, "정상적으로 삭제됐습니다.");
    }

    // NOT AUTHROIZATION
    public static BaseResponse<?> actionBadRequest() {
        return new BaseResponse<>(StatusEnum.FAIL, null, ResultCodeEnum.BAD_REQUEST, "올바른 키가 아닙니다.");
    }

    // INDEX
    public static BaseResponse<?> actionMain() {
        return new BaseResponse<>(StatusEnum.SUCCESS, "Nice to meet you stranger!", ResultCodeEnum.SUCCESS, null);
    }
}
