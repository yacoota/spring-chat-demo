package com.business.framework.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;


@Component
@Slf4j
public class StompSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {
    @Autowired
    @SuppressWarnings("all")
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
        log.info("聊天stomp订阅:{}" , sha);
    }
}