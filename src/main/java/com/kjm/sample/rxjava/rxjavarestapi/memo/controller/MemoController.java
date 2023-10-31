package com.kjm.sample.rxjava.rxjavarestapi.memo.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjm.sample.rxjava.rxjavarestapi.common.BaseResponse;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.ResultCodeEnum;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.StatusEnum;
import com.kjm.sample.rxjava.rxjavarestapi.memo.model.MemoVo;
import com.kjm.sample.rxjava.rxjavarestapi.memo.model.dto.MemoRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.memo.service.MemoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo")
public class MemoController {
    private final MemoService memoService;

    // 메모 등록
    @Operation(summary = "메모등록", description = "메모등록")
    @Parameters({
            @Parameter(name = "memoTtl", description = "메모제목"),
            @Parameter(name = "memoCtt", description = "메모내용"),
    })
    @PostMapping(value = "/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<?>> addMemo(@RequestBody MemoRequestDto memoRequestDto) {
        // 메모 등록
        memoService.addMemo(memoRequestDto);
        return new ResponseEntity<>(BaseResponse.actionCreateSuccess(), HttpStatus.CREATED);
    }

    // 메모 수정
    @Operation(summary = "메모수정", description = "메모수정")
    @Parameters({
            @Parameter(name = "memoTtl", description = "메모제목"),
            @Parameter(name = "memoCtt", description = "메모내용"),
    })
    @PostMapping(value = "/v1.0/{memoSeq}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<?>> patchMemo(@RequestBody MemoRequestDto memoRequestDto) {
        // 메모 수정
        MemoVo vo = memoService.updateMemo(memoRequestDto);

        if (vo == null) { // not found
            return new ResponseEntity<>(BaseResponse.actionNotFound(), HttpStatus.NOT_FOUND);
        } else { // success
            BaseResponse<MemoVo> responseResult = BaseResponse.<MemoVo>builder()
                    .status(StatusEnum.SUCCESS)
                    .data(vo)
                    .resultCode(ResultCodeEnum.SUCCESS)
                    .resultMsg(null)
                    .build();

            return ResponseEntity.ok(responseResult);
        }
    }

    // 메모 상세조회
    @Operation(summary = "메모 상세조회", description = "상세조회")
    @Parameters({
            @Parameter(name = "memoSeq", description = "메모일련번호"),
    })
    @GetMapping(value = "/v1.0/{memoSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<?>> getMemoDetail(@PathVariable(value = "memoSeq") Long memoSeq) {
        // 메모 상세 조회
        MemoVo vo = memoService.getMemo(memoSeq).orElse(null);
        if (vo == null) { // not found
            return new ResponseEntity<>(BaseResponse.actionNotFound(), HttpStatus.NOT_FOUND);
        } else { // success
            BaseResponse<MemoVo> responseResult = BaseResponse.<MemoVo>builder()
                    .status(StatusEnum.SUCCESS)
                    .data(vo)
                    .resultCode(ResultCodeEnum.SUCCESS)
                    .resultMsg(null)
                    .build();

            return ResponseEntity.ok(responseResult);
        }
    }

    // 메모 목록조회
    @Operation(summary = "메모 목록조회", description = "목록조회")
    @Parameters({
            @Parameter(name = "size", description = "페이지당 수"),
            @Parameter(name = "page", description = "페이지번호"),
    })
    @GetMapping(value = "/v1.0", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<?>> getMemoList(Pageable pageable) {
        BaseResponse<?> responseResult = BaseResponse.<List<MemoVo>>builder()
                .status(StatusEnum.SUCCESS)
                .data(memoService.getMemoList(pageable))
                .resultCode(ResultCodeEnum.SUCCESS)
                .resultMsg(null)
                .build();

        return ResponseEntity.ok(responseResult);
    }

    // 특정 제목을 포함하는 메모 목록 조회
    @Operation(summary = "메모 목록조회", description = "목록조회")
    @Parameters({
            @Parameter(name = "size", description = "페이지당 수"),
            @Parameter(name = "page", description = "페이지번호"),
    })
    @GetMapping(value = "/v1.0/contain/{memoTtl}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<?>> getMemoListContainTtl(Pageable pageable,
            @PathVariable(value = "memoTtl") String memoTtl) {
        BaseResponse<?> responseResult = BaseResponse.<List<MemoVo>>builder()
                .status(StatusEnum.SUCCESS)
                .data(memoService.getMemoListByTitle(memoTtl, pageable))
                .resultCode(ResultCodeEnum.SUCCESS)
                .resultMsg(null)
                .build();

        return ResponseEntity.ok(responseResult);
    }

    // 메모 삭제
    @Operation(summary = "메모 삭제", description = "메모 삭제")
    @Parameters({
            @Parameter(name = "memoSeq", description = "메모일련번호"),
    })
    @DeleteMapping(value = "/v1.0/{memoSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<?>> deleteMemo(
            @PathVariable(value = "memoSeq") Long memoSeq) {
        memoService.deleteMemo(memoSeq);
        return new ResponseEntity<>(BaseResponse.actionSuccessWithNoContent(), HttpStatus.OK);
    }

    // 특정 제목을 포함하는 메모 삭제
    @Operation(summary = "메모 제목을 포함하는 메모 삭제", description = "제목 포함")
    @Parameters({
            @Parameter(name = "memoTtl", description = "메모제목"),
            @Parameter(name = "memoSeq", description = "메모일련번호"),
    })
    @DeleteMapping(value = "/v1.0/contain/{memoTtl}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<?>> deleteMemoContainTtl(
            @PathVariable(value = "memoTtl") String memoTtl) {
        memoService.deleteMemoByTitle(memoTtl);
        return new ResponseEntity<>(BaseResponse.actionSuccessWithNoContent(), HttpStatus.OK);
    }
}
