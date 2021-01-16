package com.business.framework.socket;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.security.Principal;

@AllArgsConstructor
@NoArgsConstructor
public class WebSocketUser implements Principal {

    private String username;

    @Override
    public String getName() {
        return "chat-".concat(this.username);
    }

}
