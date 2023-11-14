package com.kjm.sample.rxjava.rxjavarestapi.member.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "T_MEMBERS")
@Entity
public class MemberVo {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PASSWORD")
    private String password;

}
