package com.kjm.sample.rxjava.rxjavarestapi.author.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kjm.sample.rxjava.rxjavarestapi.author.model.AuthorVo;
import com.kjm.sample.rxjava.rxjavarestapi.author.model.dto.AddAuthorRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.author.repository.AuthorRepository;

import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthroServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Single<String> addAuthor(AddAuthorRequestDto addAuthorRequestDto) {
        return addAuthorToRepository(addAuthorRequestDto);
    }

    private Single<String> addAuthorToRepository(AddAuthorRequestDto addAuthorRequestDto) {
        return Single.create(singleSubscriber -> {
            // 데이터 저장 후 uuid 리턴
            String addedAuthorId = authorRepository.save(toAuthor(addAuthorRequestDto)).getId();
            // onsuccess를 통해 single 연산자 리턴
            singleSubscriber.onSuccess(addedAuthorId);
        });
    }

    // authorvo 객체 생성
    private AuthorVo toAuthor(AddAuthorRequestDto addAuthorRequestDto) {
        return AuthorVo.builder()
                .id(UUID.randomUUID().toString())
                .name(addAuthorRequestDto.getName())
                .build();
    }
}
