package com.kjm.sample.rxjava.rxjavarestapi.notification.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kjm.sample.rxjava.rxjavarestapi.notification.model.dto.SendDataRequestDto;
import com.kjm.sample.rxjava.rxjavarestapi.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(value = "/v1.0/subscribe/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long id) {
        return notificationService.subscribe(id);
    }

    @PostMapping("/v1.0/send-data/{id}")
    public void sendData(@RequestBody SendDataRequestDto dto, @PathVariable Long id) {
        notificationService.notify(id, dto.toString());
    }
}
