package com.business.framework.entry;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 100657597276449919L;

    public ChatMessage(String message) {
        this.message = message;
    }

    public ChatMessage(String receiver, String message) {
        this.receiver = receiver;
        this.message = message;
    }

    private String message;

    private String sender;

    private String receiver;


    @AllArgsConstructor
    public enum Session {
        HPSESSION("HPSESSION"), WSSESSION("WSSESSION");
        @Getter
        private String name;

    }


}
