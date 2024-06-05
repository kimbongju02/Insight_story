package com.insight.pak.service;

import java.util.List;

public interface ChatGPTService {
    String generateText(String prompt);
    String horrorPrompt(String[] name);
    String continuePrompt(String prevStory, String choice);
}
