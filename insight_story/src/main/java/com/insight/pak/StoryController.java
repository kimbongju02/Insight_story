package com.insight.pak;

import java.util.List;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;

@Controller
public class StoryController {
	private StoryService storyService;
	public void getAllStory(Model model) {
		List<Story> story = storyService.getAllStory();
		model.addAttribute("story", story);
	}

	public void getStoryById(int id, Model model) {
		Story story = storyService.getStoryById(id);
		model.addAttribute("selectStory", story);
		
	}
}