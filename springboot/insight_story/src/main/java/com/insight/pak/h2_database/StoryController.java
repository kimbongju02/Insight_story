package com.insight.pak.h2_database;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class StoryController {
    @Autowired
    private final StoryService storyService;

    @GetMapping("/main/page")
    public String main_page(Model model){
        List<Story> storyList = storyService.find_all();
        model.addAttribute("storyList", storyList);
        System.out.println("storyList: " + storyList.get(0).getName() + " " + storyList.get(0).getPrompt() + " " + storyList.get(0).getSummary() + " " + storyList.get(0).getImage() + " " + storyList.get(0).getId());
        return "root_page";
    }

    @GetMapping("/story/page/{id}")
    public String story_page(Model model, @PathVariable("id") Integer id){
        Story story = storyService.find_id(id);
        model.addAttribute("story", story);
        System.out.println("story: " + story);
        return "index";
    }
}
