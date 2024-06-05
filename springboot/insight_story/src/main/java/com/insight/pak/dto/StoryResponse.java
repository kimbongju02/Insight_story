package com.insight.pak.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.*;

public class StoryResponse {
    private String story;
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private Map<String, String> dialogues = new LinkedHashMap<>();

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public Map<String, String> getDialogues() {
        return dialogues;
    }

    @JsonAnySetter
    public void setDialogue(String name, String dialogue) {
        if (!name.equals("story") && !name.equals("question") && !name.equals("choice1") && !name.equals("choice2") && !name.equals("choice3")) {
            this.dialogues.put(name, dialogue);
        }
    }
}
