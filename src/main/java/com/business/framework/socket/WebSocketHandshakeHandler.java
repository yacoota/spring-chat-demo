package com.business.framework.socket;

import com.business.framework.entry.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
@Slf4j
public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler handler, Map<String, Object> attributes) {
        String sessionId = (String) attributes.get(ChatMessage.Session.WSSESSION.getName());
        log.info("握手: {}, {}, {}, {}", request.getURI().getPath(), attributes, handler, request.getPrincipal());
        return new  com.business.framework.socket.WebSocketUser(sessionId);
    }

}
