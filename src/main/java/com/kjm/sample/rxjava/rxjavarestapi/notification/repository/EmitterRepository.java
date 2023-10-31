package com.kjm.sample.rxjava.rxjavarestapi.notification.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {
    // emitter를 저장하는 객체
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 아이디와 emitter를 저장
    public void save(Long id, SseEmitter emitter) {
        emitters.put(id, emitter);
    }

    // 주어진 아이디와 emitter를 삭제
    public void deleteById(Long id) {
        emitters.remove(id);
    }

    // 주어진 아이디와 emitter를 가져옴
    public SseEmitter findById(Long id) {
        return emitters.get(id);
    }
}
