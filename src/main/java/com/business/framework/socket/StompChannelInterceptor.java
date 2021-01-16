package com.business.framework.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StompChannelInterceptor implements ChannelInterceptor {


    /*********************************************************************************************/
    /*********************************** 发送来的消息 *******************************************/
    /*********************************************************************************************/
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        if (sha.getCommand() == StompCommand.CONNECT) {
            String token = sha.getFirstNativeHeader("token");
            com.business.framework.socket.WebSocketUser webSocketUser = ( com.business.framework.socket.WebSocketUser) sha.getUser();
            sha.getSessionAttributes().put("token", token);
            /******************* 采用 RobotSessionRegister 来处理如下关系********************/
            log.trace("发送pre connect:{},{},{}", token, webSocketUser.getName(), sha);
        } else if (sha.getCommand() == StompCommand.DISCONNECT) {
            String token = (String) sha.getSessionAttributes().get("token");
            sha.getSessionAttributes().remove("token");
            log.trace("发送pre disconnect:{},{}", token, sha);
        } else if (sha.getCommand() == StompCommand.SUBSCRIBE) {
            String token = sha.getFirstNativeHeader("token");
            log.trace("发送pre subscribe:{},{}", token, sha);
        } else if (sha.getCommand() == StompCommand.SEND) {
            String token = sha.getFirstNativeHeader("token");
            log.trace("发送pre send:{},{},{}", token, sha, sha.getUser().getName());
        } else {
            log.trace("发送pre " + sha.getCommand().toString().toUpperCase() + ":{}", sha);
        }
        return message;
    }


    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        //log.trace("发送post send:{}", sha);
        if (sha.getCommand() == null) {
            return;
        }
        //这里的sessionId和accountId对应HttpSessionIdHandshakeInterceptor拦截器的存放key

        switch (sha.getCommand()) {
            case CONNECT:
                //  connect(sessionId, accountId);
                log.info("websocket channel开启,{}", sha);
                break;
            case DISCONNECT:
                // disconnect(sessionId, accountId, sha);
                log.info("websocket channel关闭,{}", sha);
                break;
            default:
                break;
        }
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        log.trace("发送after send:{}", sha);
    }
    /*********************************************************************************************/
    /*********************************** 接收到的消息 *******************************************/
    /*********************************************************************************************/
    @Override
    public boolean preReceive(MessageChannel channel) {
        log.trace("接收pre receiver:{}", channel);
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        log.trace("接收post receiver:{}, {}", message, channel);
        return message;
    }

    @Override
    public void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex) {
        log.trace("接收after receiver:{}, {}", message, channel);
    }

    //连接成功
    private void connect(String sessionId, String accountId) {

    }

    //断开连接
    private void disconnect(String sessionId, String accountId, StompHeaderAccessor sha) {

    }
}


