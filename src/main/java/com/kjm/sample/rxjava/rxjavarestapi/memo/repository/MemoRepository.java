package com.kjm.sample.rxjava.rxjavarestapi.memo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.kjm.sample.rxjava.rxjavarestapi.memo.model.MemoVo;

import jakarta.transaction.Transactional;

public interface MemoRepository extends JpaRepository<MemoVo, Long> {
    // 특정 제목을 포함하는 메모 목록 조회
    Page<MemoVo> findAllByMemoTtlContaining(String memoTtl, Pageable pageable);

    // 일련번호 역순으로 메모 목록 조회
    Page<MemoVo> findAllByOrderByMemoSeqDesc(Pageable pageable);

    // 메모 조회
    Optional<MemoVo> findByMemoSeq(Long memoSeq);

    // 특정 제목을 포함하는 메모 삭제
    @Modifying
    @Transactional
    void deleteAllByMemoTtlContaining(String memoTtl);
}
