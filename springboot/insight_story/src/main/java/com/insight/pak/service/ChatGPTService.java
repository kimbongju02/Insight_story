package com.insight.pak.service;

import org.springframework.stereotype.Service;

@Service
public interface ChatGPTService {
    String generateText(String prompt);
    String prompt();

    String storyPrompt();
    String dialogPrompt();
    String choicePrompt();

    String continuePrompt(String prevStory, String choice);
}
