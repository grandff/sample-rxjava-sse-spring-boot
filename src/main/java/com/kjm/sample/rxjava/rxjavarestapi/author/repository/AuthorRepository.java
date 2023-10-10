package com.kjm.sample.rxjava.rxjavarestapi.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjm.sample.rxjava.rxjavarestapi.author.model.AuthorVo;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorVo, String> {

}
