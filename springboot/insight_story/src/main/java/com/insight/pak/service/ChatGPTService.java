package com.insight.pak.service;

public interface ChatGPTService {
    String generateText(String prompt);
    String prompt();
    String continuePrompt(String prevStory, String choice);
}
