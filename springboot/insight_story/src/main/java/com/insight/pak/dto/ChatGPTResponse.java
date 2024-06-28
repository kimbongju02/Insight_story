package com.insight.pak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * ChatGPTResponse
 * OpenAI API 응답 Dto
 *
 * story
 * choice
 * */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatGPTResponse {
    private String story;
    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;
        private Message message;
		public Message getMessage() {
			return message;
		}
    }
}
