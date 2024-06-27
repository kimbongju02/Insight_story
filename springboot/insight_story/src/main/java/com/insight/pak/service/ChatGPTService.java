package com.insight.pak.service;

import jakarta.servlet.http.HttpSession;

public interface ChatGPTService {

    Boolean saveApiKey(HttpSession session, String apiKey);
    String getApiKey(HttpSession session);

    String generateText(String prompt);
    String Prompt(String prompt);
    String continuePrompt(String prevStory, String choice);
}
