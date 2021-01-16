package com.business.framework.socket;

import com.business.framework.entry.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
@Slf4j
public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> attributes) throws Exception {
        // 解决The extension [x-webkit-deflate-frame] is not supported问题
        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        if (request instanceof ServletServerHttpRequest) {
            attributes.put(ChatMessage.Session.WSSESSION.getName(), wssessionId(request));
            log.info("聊天登录用户web session信息: {}, {}, {}, {}", request.getURI().getPath(), attributes, handler, request.getPrincipal());
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Exception ex) {

    }

    //  获取httpsession
    private HttpSession hpsessionId(ServletServerHttpRequest request) {
        return request.getServletRequest().getSession(false);
    }

    // 连接中取值websocketsession，非常规手段，寻找替代方法。。。
    private String wssessionId(ServerHttpRequest request) {
        return request.getURI().getPath().split("/")[3];
    }


}

