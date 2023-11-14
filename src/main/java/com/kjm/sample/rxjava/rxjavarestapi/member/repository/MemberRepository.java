package com.kjm.sample.rxjava.rxjavarestapi.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjm.sample.rxjava.rxjavarestapi.member.model.MemberVo;

@Repository
public interface MemberRepository extends JpaRepository<MemberVo, String> {
	
}
