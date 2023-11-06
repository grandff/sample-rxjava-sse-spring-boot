package com.kjm.sample.rxjava.rxjavarestapi.book.model;

import com.kjm.sample.rxjava.rxjavarestapi.author.model.AuthorVo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "T_BOOKS")
@Entity(name = "T_BOOKS")
public class BookVo {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID")
    private AuthorVo author;
}
