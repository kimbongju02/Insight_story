package com.insight.pak;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StoryDTO {
	Integer id;
	String name;
	String prompt;
	String summary;
	String link;
}