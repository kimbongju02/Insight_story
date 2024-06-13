package com.insight.pak.h2_database;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class StoryController {
    @Autowired
    private StoryService storyService;

    public List<Story> load_all_data(){
        List<Story> story_list = storyService.find_all();
        return story_list;
    }

    public Story load_select_story(String id){
        Story story = storyService.find_id(Integer.parseInt(id));
        return story;
    }
}
