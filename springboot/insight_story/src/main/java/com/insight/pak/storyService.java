package com.insight.pak;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class storyService {
    @Autowired
    private storyRepository storyRepository;
    public List<story> getAllStory() {
        return storyRepository.findAll();
    }

    public story getStoryById(int id) {
        return storyRepository.findById(id).get();
    }
}
