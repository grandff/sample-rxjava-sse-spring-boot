package com.kjm.sample.rxjava.rxjavarestapi.book.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kjm.sample.rxjava.rxjavarestapi.author.model.AuthorVo;
import com.kjm.sample.rxjava.rxjavarestapi.author.repository.AuthorRepository;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.BookVo;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.AddBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.BookResponseDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.model.dto.UpdateBookRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.book.repository.BookRepository;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    // 책등록
    @Override
    public Single<String> addBook(AddBookRequestDto addBookRequestDto) {
        return Single.create(singleSubscriber -> {
            Optional<AuthorVo> authorVo = authorRepository.findById(addBookRequestDto.getAuthorId());
            if (!authorVo.isPresent()) {
                singleSubscriber.onError(new EntityNotFoundException("NOT FOUND AUTHOR"));
            } else {
                String addedBookId = bookRepository.save(toBook(addBookRequestDto)).getId();
                singleSubscriber.onSuccess(addedBookId);
            }
        });
    }

    // 책 객체 생성
    private BookVo toBook(AddBookRequestDto addBookRequestDto) {
        BookVo book = BookVo.builder()
                .id(UUID.randomUUID().toString())
                .title(addBookRequestDto.getTitle())
                .author(AuthorVo.builder().id(addBookRequestDto.getAuthorId()).build())
                .build();
        return book;
    }

    // 책수정
    @Override
    public Completable updateBook(UpdateBookRequestDto updateBookRequestDto) {
        return Completable.create(completableSubscriber -> {
            Optional<BookVo> bookVo = bookRepository.findById(updateBookRequestDto.getId());
            if (!bookVo.isPresent()) {
                completableSubscriber.onError(new EntityNotFoundException());
            } else {
                BookVo book = bookVo.get();
                book.setTitle(updateBookRequestDto.getTitle());
                bookRepository.save(book);
                completableSubscriber.onComplete();
            }
        });
    }

    // 목록조회
    @Override
    public Single<List<BookResponseDto>> getAllBooks(int limit, int page) {
        return findAllBooksInRepository(limit, page).map(this::toBookResponseList);
    }

    private Single<List<BookVo>> findAllBooksInRepository(int limit, int page) {
        return Single.create(singleSubscriber -> {
            List<BookVo> bookList = bookRepository.findAll(PageRequest.of(page, limit)).getContent();
            singleSubscriber.onSuccess(bookList);
        });
    }

    private List<BookResponseDto> toBookResponseList(List<BookVo> bookList) {
        return bookList
                .stream()
                .map(this::toBookResponseDto)
                .collect(Collectors.toList());
    }

    // response dto에 맞게 mapping
    private BookResponseDto toBookResponseDto(BookVo book) {
        BookResponseDto responseDto = new BookResponseDto();
        BeanUtils.copyProperties(book, responseDto);
        responseDto.setAuthorName(book.getAuthor().getName()); // 작가이름만 직접 설정
        return responseDto;
    }

    // 상세조회
    @Override
    public Single<BookResponseDto> getBookDetail(String id) {
        return findBookDetailInRepository(id);
    }

    private Single<BookResponseDto> findBookDetailInRepository(String id) {
        return Single.create(singleSubscriber -> {
            Optional<BookVo> bookVo = bookRepository.findById(id);
            if (!bookVo.isPresent()) {
                singleSubscriber.onError(new EntityNotFoundException());
            } else {
                BookResponseDto responseDto = toBookResponseDto(bookVo.get());
                singleSubscriber.onSuccess(responseDto);
            }
        });
    }

    // 삭제
    @Override
    public Completable deleteBook(String id) {
        return deleteBookInRepository(id);
    }

    private Completable deleteBookInRepository(String id) {
        return Completable.create(completableSubscriber -> {
            Optional<BookVo> optionalBook = bookRepository.findById(id);
            if (!optionalBook.isPresent()) {
                completableSubscriber.onError(new EntityNotFoundException());
            } else {
                bookRepository.delete(optionalBook.get());
                completableSubscriber.onComplete();
            }
        });
    }

}
