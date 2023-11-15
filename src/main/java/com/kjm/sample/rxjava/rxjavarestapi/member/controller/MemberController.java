package com.kjm.sample.rxjava.rxjavarestapi.member.controller;


import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kjm.sample.rxjava.rxjavarestapi.book.model.BookVo;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.AddBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.BookResponseDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.UpdateBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.repository.BookRepository;
import com.kjm.sample.rxjava.rxjavarestapi.book.service.BookService;
import com.kjm.sample.rxjava.rxjavarestapi.common.BaseResponse;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.ResultCodeEnum;
import com.kjm.sample.rxjava.rxjavarestapi.common.enums.StatusEnum;
import com.kjm.sample.rxjava.rxjavarestapi.member.model.dto.AddMemberRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.member.model.dto.MemberResponseDto;
import com.kjm.sample.rxjava.rxjavarestapi.member.model.dto.UpdateMemberRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.member.service.MemberService;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
        private final MemberService memberService;
        

        @Operation(summary = "member 추가", description = "member 추가")
        @Parameters({
                        @Parameter(name = "id", description = "사용자 ID"),
                        @Parameter(name = "password", description = "사용자 Password"),
        })
        @PostMapping(value = "/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public Single<ResponseEntity<BaseResponse<?>>> addMember(@RequestBody AddMemberRequestDto addMemberRequestDto) {
                return memberService.addMember(addMemberRequestDto)
                		.subscribeOn(Schedulers.io())
                		.map(memberId -> ResponseEntity.created(URI.create("/api/member/" + memberId))
                                                .body(BaseResponse.actionCreateSuccess()));
        }

        
        
        @Operation(summary = "member 수정", description = "member 수정")
        @Parameters({
		        	@Parameter(name = "id", description = "사용자 ID"),
		            @Parameter(name = "password", description = "사용자 Password"),
        })
        @PatchMapping(value = "/v1.0/{memberId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public Single<ResponseEntity<BaseResponse<?>>> updateMember(@RequestBody UpdateMemberRequestDto updateMemberRequestDto, @PathVariable(value = "memberId") String memberId) {
                return memberService.updateMember(updateMemberRequestDto)
                		.subscribeOn(Schedulers.io())
                        .toSingle(() -> ResponseEntity.ok(BaseResponse.actionSuccess()));
        }
        
        
        
 
        @Operation(summary = "Member 상세조회", description = "Member 상세 조회")
        @Parameters({
                        @Parameter(name = "memberId", description = "사용자 ID"),
        })
        @GetMapping(value = "/v1.0/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public Single<ResponseEntity<BaseResponse<?>>> getMemberDetail(@PathVariable(value = "memberId") String memberId) {
                return memberService.getMemberDetail(memberId)
                                .subscribeOn(Schedulers.io())
                                .map(response -> ResponseEntity.ok(BaseResponse.<MemberResponseDto>builder()
                                                .status(StatusEnum.SUCCESS)
                                                .data(response)
                                                .resultCode(ResultCodeEnum.SUCCESS)
                                                .resultMsg("Success")
                                                .build()));
        }
        
        
        @Operation(summary = "Member 삭제", description = "Member 삭제")
        @Parameters({
                        @Parameter(name = "memberId", description = "사용자 ID"),
        })
        @DeleteMapping(value = "/v1.0/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public Single<ResponseEntity<BaseResponse<?>>> deleteBOok(@PathVariable(value = "memberId") String memberId) {
                return memberService.deleteMember(memberId)
                                .subscribeOn(Schedulers.io())
                                .toSingle(() -> ResponseEntity.ok(BaseResponse.actionSuccessWithNoContent()));
        }

        
}