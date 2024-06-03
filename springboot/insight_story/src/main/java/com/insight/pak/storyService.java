package com.insight.pak;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class StoryService {
    @Autowired
    private StoryRepository storyRepository;
    private StoryDTO storyDTO;
    public List<StoryDTO> getAllStory() {
        return storyRepository.findAll();
    }

    public StoryDTO getStoryById(int id) {
        return storyRepository.findById(id).get();
    }
}
