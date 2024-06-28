package com.insight.pak.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Choice
 * GPT가 제공한 선택지를 선택하기위한 Dto
 *
 * message
 * */

@Data
@Getter
@Setter
public class Choice {
    private Message message;
}
