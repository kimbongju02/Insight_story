package com.insight.pak;

import java.util.List;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;

@Controller
public class StoryController {
	private StoryService storyService;
	
	public void getAllStory(Model model) {
		List<StoryDTO> story = storyService.getAllStory();
		model.addAttribute("story", story);
	}

	public void getStoryById(int id, Model model) {
		StoryDTO story = storyService.getStoryById(id);
		model.addAttribute("selectStory", story);
		story.getPrompt();	
	}
}