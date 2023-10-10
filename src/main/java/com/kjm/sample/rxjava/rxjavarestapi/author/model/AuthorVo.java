package com.kjm.sample.rxjava.rxjavarestapi.author.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Entity(name = "T_AUTHORS")
public class AuthorVo {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;
}
