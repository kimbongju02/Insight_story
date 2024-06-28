package com.insight.pak.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Message
 * (TEST)
 *
 * role
 * content
 * */

@Data
@NoArgsConstructor
@Getter
@Setter
public class Message {
    private String role;
    private String content;
    
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
