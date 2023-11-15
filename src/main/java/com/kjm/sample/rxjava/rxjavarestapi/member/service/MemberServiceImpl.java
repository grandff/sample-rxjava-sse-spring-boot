package com.kjm.sample.rxjava.rxjavarestapi.member.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kjm.sample.rxjava.rxjavarestapi.author.model.AuthorVo;
import com.kjm.sample.rxjava.rxjavarestapi.author.repository.AuthorRepository;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.BookVo;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.AddBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.BookResponseDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.UpdateBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.repository.BookRepository;
import com.kjm.sample.rxjava.rxjavarestapi.member.model.MemberVo;
import com.kjm.sample.rxjava.rxjavarestapi.member.model.dto.AddMemberRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.member.model.dto.MemberResponseDto;
import com.kjm.sample.rxjava.rxjavarestapi.member.model.dto.UpdateMemberRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.member.repository.MemberRepository;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    //추가
	@Override
	public Single<String> addMember(AddMemberRequestDto addMemberRequestDto) {		
		return Single.create(singleSubscriber ->{
			Optional<MemberVo> memberVo = memberRepository.findById(addMemberRequestDto.getId());
			if (memberVo.isPresent()) {
	           	//이미 존재함
				singleSubscriber.onError(new EntityExistsException());				
            }else {
            	String addMemberId = memberRepository.save(toMember(addMemberRequestDto)).getId();
                singleSubscriber.onSuccess(addMemberId);
            }
		});
	}
	
	// 사용자 객체 생성
    private MemberVo toMember(AddMemberRequestDto addMemberRequestDto) {
        MemberVo member = MemberVo.builder()
                .id(addMemberRequestDto.getId())
                .password(addMemberRequestDto.getPassword())
                .build();
        return member;
    }
    
    
   
    
    //업데이트
	@Override
	public Completable updateMember(UpdateMemberRequestDto updateMemberRequestDto) {		
		return Completable.create(completableSubscriber ->{
			Optional<MemberVo> memberVo = memberRepository.findById(updateMemberRequestDto.getId());
			if (!memberVo.isPresent()) {
				completableSubscriber.onError(new EntityNotFoundException());				
            }else {
            	 MemberVo member = memberVo.get();
            	 member.setId(updateMemberRequestDto.getId());
            	 member.setPassword(updateMemberRequestDto.getPassword());
            	 memberRepository.save(member);
            	 completableSubscriber.onComplete();
            }
		});
	}
	
    
    // 상세조회
    @Override
    public Single<MemberResponseDto> getMemberDetail(String id) {
        return findMemeberDetailInRepository(id);
    }

    private Single<MemberResponseDto> findMemeberDetailInRepository(String id) {
        return Single.create(singleSubscriber -> {
            Optional<MemberVo> memberVo = memberRepository.findById(id);
            if (!memberVo.isPresent()) {
                singleSubscriber.onError(new EntityNotFoundException());
            } else {
                MemberResponseDto responseDto = toMemberResponseDto(memberVo.get());
                singleSubscriber.onSuccess(responseDto);
            }
        });
    }
    
    
    // response dto에 맞게 mapping
    private MemberResponseDto toMemberResponseDto(MemberVo member) {
        MemberResponseDto responseDto = new MemberResponseDto();
        BeanUtils.copyProperties(member, responseDto);
        return responseDto;
    }
    
    // 삭제
    @Override
    public Completable deleteMember(String id) {
        return deleteBookInRepository(id);
    }

    private Completable deleteBookInRepository(String id) {
        return Completable.create(completableSubscriber -> {
            Optional<MemberVo> optionalMember = memberRepository.findById(id);
            if (!optionalMember.isPresent()) {
                completableSubscriber.onError(new EntityNotFoundException());
            } else {
            	memberRepository.delete(optionalMember.get());
                completableSubscriber.onComplete();
            }
        });
    }

}
