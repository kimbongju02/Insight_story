package com.insight.pak.service;

public interface ChatGPTService {
    String generateText(String prompt);
    String Prompt();
    String continuePrompt(String prevStory, String choice);
}
