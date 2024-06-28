package com.insight.pak.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.pak.dto.StoryRequest;
import com.insight.pak.dto.StoryResponse;
import com.insight.pak.h2_database.StoryController;
import com.insight.pak.service.ChatGPTService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 컨트롤러
 * ChatGPTController
 * <p>
 * postGenerateStory 메서드 : 프롬프트를 생성하여 소설과 선택지를 만들어 텍스트로 반환
 * selectChoice 메서드 : 이전 프롬프트(소설, 선택지) 기반으로 이어지는 다음 이야기와 선택지를 반환
 */

@Controller// Spring 컨트롤러 선언
public class ChatGPTController {

	@Autowired
    private ChatGPTService chatGPTService;
	@Autowired
	private StoryController storyController;

    // API 구동 테스트를 위한 GET 메서드
    @GetMapping("/chat") // GET 요청에 대한 핸들러 메서드. "/chat"
    public String chat(@RequestParam(name = "prompt") String prompt) {
        return chatGPTService.generateText(prompt);
    }

    // content 페이지 조회
    @GetMapping("/content/{id}")
    public String getGenerateStory(@PathVariable("id") String id,Model model){
        model.addAttribute("story_id", id);
        return "content";
    }

    // 지정된 prompt를 통해서 시작 이야기 생성
    @ResponseBody
    @GetMapping("/generate/init/{id}")
    public StoryResponse getStory(@PathVariable("id") String id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StoryResponse storyResponse = new StoryResponse();

        String select_story_prompt = storyController.load_select_story(id).getPrompt();
        //System.out.println("-------------------select_story_prompt---------------------\n"+select_story_prompt);

        String initialPrompt = chatGPTService.Prompt(select_story_prompt);
        String initialStory = chatGPTService.generateText(initialPrompt);
        //String initialStory = test_story;
        if(initialStory=="API Error"){
            return storyResponse;
        }
        if (initialStory.startsWith("출력문:")) {
            initialStory = initialStory.substring(4);
        }
        //System.out.println("-------------------create init story---------------------\n"+initialStory);

        
        // JSON 형식 확인 및 JSON 데이터를 객체로 변환
        try {
            new ObjectMapper().readTree(initialStory);
            storyResponse = objectMapper.readValue(initialStory, StoryResponse.class);
        } catch (JsonParseException e) { // JSON 형식 오류
            //System.out.println("JSON 형식 오류: " + e.getMessage());
            return getStory(id);
        } catch (Exception e) { // JSON 데이터를 객체로 변환 오류
            e.printStackTrace();
        }
        
        return storyResponse;
    }
    
    // html에서 이전 스토리의 json 형태와 선택지를 전달받아 다음 이야기와 선택지를 생성
    @ResponseBody
    @PostMapping("/generate/story")
    public StoryResponse get_next_story(@RequestBody StoryRequest storyRequest)  throws JsonProcessingException {
        String data = storyRequest.getData();
        String choice = storyRequest.getChoice();
        //System.out.println("-------------------recieve story to html---------------------\n"+data);
        //System.out.println("-------------------recieve choice to html---------------------\n"+choice);

        String continuePrompt = chatGPTService.continuePrompt(data, choice);
        String nextStory = chatGPTService.generateText(continuePrompt);
        //System.out.println("-------------------send data about next story---------------------\n"+nextStory);

        ObjectMapper objectMapper = new ObjectMapper();
        StoryResponse storyResponse = new StoryResponse();
        // JSON 형식 확인 및 JSON 데이터를 객체로 변환
        try {
            new ObjectMapper().readTree(nextStory);
            storyResponse = objectMapper.readValue(nextStory, StoryResponse.class);
        } catch (JsonParseException e) { // JSON 형식 오류
            //System.out.println("JSON 형식 오류: " + e.getMessage());
            return get_next_story(storyRequest);
        } catch (Exception e) { // JSON 데이터를 객체로 변환 오류
            e.printStackTrace();
        }
        
        return storyResponse;
    }
}