package com.insight.pak.h2_database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StoryService {
    @Autowired
    private final StoryRepository storyRepository;

    public List<Story> find_all() {
        return storyRepository.findAll();    
    }

    public Story find_id(Integer id) {
        return storyRepository.findById(id).get();
    }
}
