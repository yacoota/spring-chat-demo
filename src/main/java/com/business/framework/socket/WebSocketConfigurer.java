package com.business.framework.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigurer implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private HandshakeInterceptor httpHandshakeInterceptor;
    @Autowired
    private ChannelInterceptor stompChannelInterceptor;
    @Autowired
    private HandshakeHandler websocketHandshakeHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").setHandshakeHandler(websocketHandshakeHandler).withSockJS().setInterceptors(httpHandshakeInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 订阅Broker名称：一般/topic 代表发布广播，即群发 ；queue 代表点对点，即发指定用户
        registry.enableSimpleBroker("/topic", "/queue");
        //  全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/application");
        //  点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user
        registry.setUserDestinationPrefix("/user");
        // 设置客户端订阅消息的基础路径
        registry.setPathMatcher(new AntPathMatcher("."));
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(5 * 1024).setSendBufferSizeLimit(5 * 1024).setSendTimeLimit(30 * 1000);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4).maxPoolSize(50).keepAliveSeconds(60);
        registration.interceptors(stompChannelInterceptor);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
    }

}
