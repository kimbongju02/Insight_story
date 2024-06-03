package com.insight.pak;

import java.util.List;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;

@Controller
public class StoryController {
	private StoryService storyService;
	private StoryDTO storyDTO;
	public void getAllStory(Model model) {
		List<storyDTO> story = storyService.getAllStory();
		model.addAttribute("story", story);
	}

	public void getStoryById(int id, Model model) {
		storyDTO story = storyService.getStoryById(id);
		model.addAttribute("selectStory", story);
		story.getPrompt();	
	}
}
SortCategory
sort_category
sortCategory