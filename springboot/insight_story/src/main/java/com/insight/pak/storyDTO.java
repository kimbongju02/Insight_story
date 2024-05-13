package com.insight.pak;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class storyDTO {
	Integer id;
	String name;
	String prompt;
	String summary;
	Integer image;
}
