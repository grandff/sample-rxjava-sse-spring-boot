package com.kjm.sample.rxjava.rxjavarestapi.memo.model;

import java.time.LocalDateTime;

import com.kjm.sample.rxjava.rxjavarestapi.common.BaseVo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "T_MEMO")
@Entity(name = "T_MEMO")
public class MemoVo extends BaseVo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // INFO. 자동으로 생성하기 때문에 GeneratedValue 추가
    @Column(name = "MEMO_SEQ")
    private Long memoSeq; // 메모일련번호

    @Column(name = "MEMO_TTL")
    private String memoTtl; // 메모제목

    @Column(name = "MEMO_CTT")
    private String memoCtt; // 메모내용

    // base column create
    @PrePersist
    protected void onCreate() {
        if (this.getFrstRegDt() == null) {
            this.setFrstRegDt(LocalDateTime.now());
        }
        if (this.getLstChgDt() == null) {
            this.setLstChgDt(LocalDateTime.now());
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.setLstChgDt(LocalDateTime.now());
    }
}
