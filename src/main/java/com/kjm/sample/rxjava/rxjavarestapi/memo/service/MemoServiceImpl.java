package com.kjm.sample.rxjava.rxjavarestapi.memo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kjm.sample.rxjava.rxjavarestapi.memo.model.MemoVo;
import com.kjm.sample.rxjava.rxjavarestapi.memo.model.dto.MemoRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.memo.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {
    private final MemoRepository memoRepository;

    // 메모 등록
    @Override
    public MemoVo addMemo(MemoRequestDto memoRequestDto) {
        MemoVo vo = MemoVo.builder()
                .memoTtl(memoRequestDto.getMemoTtl())
                .memoCtt(memoRequestDto.getMemoCtt())
                .build();

        return memoRepository.save(vo);
    }

    // 메모수정
    @Override
    public MemoVo updateMemo(MemoRequestDto memoRequestDto) {
        // 메모 데이터 있는지 확인
        Optional<MemoVo> memoVo = memoRepository.findByMemoSeq(memoRequestDto.getMemSeq());
        if (!memoVo.isPresent())
            return null;

        memoVo.get().setMemoTtl(memoRequestDto.getMemoTtl());
        memoVo.get().setMemoCtt(memoRequestDto.getMemoCtt());
        return memoRepository.save(memoVo.get());
    }

    // 메모 조회
    @Override
    public Optional<MemoVo> getMemo(Long memoSeq) {
        return memoRepository.findByMemoSeq(memoSeq);
    }

    // 메모 목록 조회
    @Override
    public List<MemoVo> getMemoList(Pageable pageable) {
        return memoRepository.findAllByOrderByMemoSeqDesc(pageable).getContent();
    }

    // 특정 제목을 포함하는 메모 목록 조회
    @Override
    public List<MemoVo> getMemoListByTitle(String memoTtl, Pageable pageable) {
        return memoRepository.findAllByMemoTtlContaining(memoTtl, pageable).getContent();
    }

    // 메모 삭제
    @Override
    public void deleteMemo(Long memoSeq) {
        Optional<MemoVo> memoVo = memoRepository.findByMemoSeq(memoSeq);
        if (memoVo.isPresent())
            memoRepository.delete(memoVo.get());
    }

    // 특정 제목을 포함하는 메모 삭제
    @Override
    public void deleteMemoByTitle(String memoTtl) {
        memoRepository.deleteAllByMemoTtlContaining(memoTtl);
    }

	@Override
	public void getAllBooks(int anyInt, int anyInt2) {
		// TODO Auto-generated method stub
		
	}
}
