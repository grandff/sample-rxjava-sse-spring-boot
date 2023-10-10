package com.kjm.sample.rxjava.rxjavarestapi.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjm.sample.rxjava.rxjavarestapi.book.model.BookVo;

@Repository
public interface BookRepository extends JpaRepository<BookVo, String> {
    List<BookVo> findAllByAuthorId(String authorId);
}
