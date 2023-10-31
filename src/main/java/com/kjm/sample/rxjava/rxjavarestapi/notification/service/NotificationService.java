package com.kjm.sample.rxjava.rxjavarestapi.notification.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kjm.sample.rxjava.rxjavarestapi.notification.repository.EmitterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    // 기본 타임아웃 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;

    // 클라이언트가 구독을 위해 호출
    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = createEmitter(userId);
        // 구독하자마자 데이터를 전송안하면 재연결 요청을 보내거나 요청 자체에서 오류가 나기 때문
        sendToClient(userId, "EventStream Created. [userId: " + userId + "]");
        return emitter;
    }

    // 서버의 이벤트를 클라이언트에게 보내는 메서드
    // 다른 서비스 로직에서 이 메서드를 사용해 데이터를 Object event에 넣고 전송하면 됨
    public void notify(Long userId, Object event) {
        sendToClient(userId, event);
    }

    // 클라이언트에게 데이터를 전송
    private void sendToClient(Long id, Object data) {
        SseEmitter emitter = emitterRepository.findById(id);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(id)).name("sse").data(data));
            } catch (IOException e) {
                emitterRepository.deleteById(id);
                emitter.completeWithError(e);
            }
        }
    }

    // 사용자 아이디를 기반으로 이벤트 emitter를 생성
    private SseEmitter createEmitter(Long id) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id, emitter);

        // emitter가 완료 될때 (모든 데이터가 성공적으로 전송될 때) emitter를 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        // emitter가 타임아웃 되었을 때 (지정된 시간 동안 어떠한 이벤트도 전송되지 않았을 때) emitter를 삭제
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        return emitter;
    }
}
