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

    // db에서 모든 스토리 값 가져옴
    public List<Story> find_all() {
        return storyRepository.findAll();    
    }

    // db에서 사용자가 선택한 id와 동일한 스토리 하나만 가져옴
    public Story find_id(Integer id) {
        return storyRepository.findById(id).orElse(null);
    }
}
