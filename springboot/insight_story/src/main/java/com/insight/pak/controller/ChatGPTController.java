package com.insight.pak.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.pak.dto.StoryResponse;
import com.insight.pak.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 컨트롤러
 * ChatGPTController
 * <p>
 * postGenerateStory 메서드 : 프롬프트를 생성하여 소설과 선택지를 만들어 텍스트로 반환
 * selectChoice 메서드 : 이전 프롬프트(소설, 선택지) 기반으로 이어지는 다음 이야기와 선택지를 반환
 */

@Controller// Spring 컨트롤러 선언
public class ChatGPTController {
    String test_story = "{\r\n" + //
                "\"story\": \"현대 대학교에서는 다양한 인물들의 이야기가 교차하고 있었다. 서윤지는 성실하고 조용한 성격으로 대학교 3학년 생활을 시작했다. 그녀는 유학 후 돌아와서 주변 사람들과의 관계에 얽히면서 새로운 시작을 하게 되었다. 한편, 강준호는 차가운 성격과 뛰어난 두뇌를 가지고 있었다. 겉보기에는 완벽해 보이지만 그의 내면에는 어두운 면모가 있었다. 그는 서윤지와의 관계에서 특별한 흥미를 느끼기 시작했다. 또 다른 캐릭터, 이태민은 윤지의 고등학교 동창으로 활발하고 사교적인 성격을 가졌다. 오랜만에 윤지를 만나 그녀를 돕고 보호하려 한다.\",\r\n" + //
                "\"dialogue\": [\r\n" + //
                "{\r\n" + //
                "\"name\": \"이태민\",\r\n" + //
                "\"content\": \"윤지야, 오랜만이야. 너무 반가워.\"\r\n" + //
                "},\r\n" + //
                "{\r\n" + //
                "\"name\": \"서윤지\",\r\n" + //
                "\"content\": \"태민이, 너무 오랜만이다. 고마워.\"\r\n" + //
                "},\r\n" + //
                "{\r\n" + //
                "\"name\": \"강준호\",\r\n" + //
                "\"content\": \"안녕, 윤지야.\"\r\n" + //
                "}\r\n" + //
                "],\r\n" + //
                "\"question\": \"이야기를 계속하려면, 윤지는 누구에게 더 가까이 다가갈까요?\",\r\n" + //
                "\"choice1\": \"강준호\",\r\n" + //
                "\"choice2\": \"이태민\",\r\n" + //
                "\"choice3\": \"아무에게도 다가가지 않는다\"\r\n" + //
                "}";

    @Autowired
    private ChatGPTService chatGPTService;

    private String prevStory; // 이전 이야기를 유지하는 변수

    // API 구동 테스트를 위한 GET 메서드
    @GetMapping("/chat") // GET 요청에 대한 핸들러 메서드. "/chat"
    public String chat(@RequestParam(name = "prompt") String prompt) {
        return chatGPTService.generateText(prompt);
    }

    // content 페이지 조회
    @GetMapping("/testpage")
    public String getGenerateStory(){
        return "testpage";
    }
    @PostMapping(value = "/testpage")
    public String postGenerateStory(){
        return "testpage";
    }

    @ResponseBody
    @GetMapping("/api/story")
    public StoryResponse getStory() throws JsonProcessingException {
        String initialPrompt = chatGPTService.Prompt();
        String initialStory = chatGPTService.generateText(initialPrompt);
        System.out.println("initialStory: " + initialStory);

        // JSON 형식 확인
        try {
            new ObjectMapper().readTree(initialStory);
        } catch (JsonParseException e) {
            System.out.println("JSON 형식 오류: " + e.getMessage());
        }

        // 선택지를 통해 스토리를 이어나가기 위해 현재 이야기를 업데이트(변수화)
        prevStory = initialStory;

        ObjectMapper objectMapper = new ObjectMapper();
        StoryResponse storyResponse = new StoryResponse();
        try{
            // JSON 데이터를 객체로 변환
            storyResponse = objectMapper.readValue(initialStory, StoryResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return storyResponse;
    }

    @ResponseBody
    @PostMapping("/select_choice")
    public void selectChoice(@RequestBody String choice){
        System.out.println("choice: " + choice);
    }

    @PostMapping("/generateStorys")
    public ResponseEntity<?> select(@RequestParam Map<String, String> requestBody, Model model) {
        String choice = requestBody.get("choice");

        String continuePrompt = chatGPTService.continuePrompt(prevStory, choice);
        String nextStory = chatGPTService.generateText(continuePrompt);

        prevStory = nextStory;

        ResponseEntity<String> currentStory = ResponseEntity.ok().body(nextStory);
        return currentStory;
    }
}