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

    // db에서 모든 스토리 값 가져옴
    public List<Story> load_all_data(){
        List<Story> story_list = storyService.find_all();
        return story_list;
    }

    // db에서 사용자가 선택한 id와 동일한 스토리 하나만 가져옴
    public Story load_select_story(String id){
        Story story = storyService.find_id(Integer.parseInt(id));
        return story;
    }
}
