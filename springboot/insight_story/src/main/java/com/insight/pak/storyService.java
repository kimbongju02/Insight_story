package com.insight.pak;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class StoryService {
    @Autowired
    private StoryRepository storyRepository;
    
    public List<Story> getAllStory() {
        return storyRepository.findAll();
    }

    public Story getStoryById(int id) {
        return storyRepository.findById(id).get();
    }
}
