package com.insight.pak;

import java.util.List;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;

@Controller
public class storyController {
	private storyService storyService;
	public void getAllStory(Model model) {
		List<story> story = storyService.getAllStory();
		model.addAttribute("story", story);
	}

	public void getStoryById(int id, Model model) {
		story story = storyService.getStoryById(id);
		model.addAttribute("selectStory", story);
	}
}
