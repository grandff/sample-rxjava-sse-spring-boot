package com.kjm.sample.rxjava.rxjavarestapi.member.service;

import java.util.List;

import com.kjm.sample.rxjava.rxjavarestapi.member.model.dto.AddMemberRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.member.model.dto.MemberResponseDto;
import com.kjm.sample.rxjava.rxjavarestapi.member.model.dto.UpdateMemberRequestDto;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface MemberService {

	//추가
	Single<String> addMember(AddMemberRequestDto addBookRequestDto);
	
	//업데이트
	Completable updateMember(UpdateMemberRequestDto updateBookRequestDto);
	
	//상세조회
	Single<MemberResponseDto> getMemberDetail(String id);
	
	//삭제
	Completable deleteMember(String id);
}
