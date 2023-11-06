package com.kjm.sample.rxjava.rxjavarestapi.author.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table (name = "T_AUTHORS")
@Entity(name = "T_AUTHORS")
public class AuthorVo {

    @Id
    @Column(name = "AUTHOR_ID")
    private String id;

    @Column(name = "NAME")
    private String name;
}
