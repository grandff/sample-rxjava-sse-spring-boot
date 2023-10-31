package com.kjm.sample.rxjava.rxjavarestapi.memo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.kjm.sample.rxjava.rxjavarestapi.memo.model.MemoVo;
import com.kjm.sample.rxjava.rxjavarestapi.memo.model.dto.MemoRequestDto;

public interface MemoService {
    // 메모 추가
    MemoVo addMemo(MemoRequestDto memoRequestDto);

    // 메모 수정
    MemoVo updateMemo(MemoRequestDto memoRequestDto);

    // 메모 조회
    Optional<MemoVo> getMemo(Long memoSeq);

    // 메모 목록 조회 (전체)
    List<MemoVo> getMemoList(Pageable pageable);

    // 특정 제목을 포함하는 메모 목록 조회
    List<MemoVo> getMemoListByTitle(String memoTtl, Pageable pageable);

    // 메모 삭제
    void deleteMemo(Long memoSeq);

    // 특정 제목을 포함하는 메모 삭제
    void deleteMemoByTitle(String memoTtl);
}
