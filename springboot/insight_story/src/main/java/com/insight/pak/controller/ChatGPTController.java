package com.insight.pak.controller;

import com.insight.pak.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * ChatGPTController
 * Rest 컨트롤러
 *
 * postGenerateStory 메서드 : 프롬프트를 생성하여 소설과 선택지를 만들어 텍스트로 반환
 * selectChoice 메서드 : 이전 프롬프트(소설, 선택지) 기반으로 이어지는 다음 이야기와 선택지를 반환
 * */

@Controller// Spring 컨트롤러 선언
@RequestMapping("/")
public class ChatGPTController {

    @Autowired
    private ChatGPTService chatGPTService;

    private String prevStory; // 이전 이야기를 유지하는 변수

    // API 구동 테스트를 위한 GET 메서드
    @GetMapping("/chat") // GET 요청에 대한 핸들러 메서드. "/chat"
    public String chat(@RequestParam(name = "prompt") String prompt) {
        return chatGPTService.generateText(prompt);
    }

    // 조회
    @GetMapping("/")
    public String getGenerateStory() {
        return "content";
    }

//    @PostMapping(value = "/generateStory")
//    public String postGenerateStory(@RequestParam Map<String, String> requestBody) {
//        // 프롬프트 기반 초기 스토리 작성
//        String initialPrompt = chatGPTService.prompt();
//        String initialStory = chatGPTService.generateText(initialPrompt);
//        System.out.println("initialStory: " + initialStory);
//
//        // 줄거리, 대화, 선택지 - JSON 형태의 문자열로 반환
//        String jsonResponse = "{\"Main\": \"" + initialStory + "\"}";
//
//        // JSON 형식 출력문(문자열) 반환
//        return jsonResponse;
//    }

    // Post
    @PostMapping(value = "/content")
    public String postGenerateStory(@RequestParam Map<String, String> requestBody, Model model) {
        // @RequestParam 어노테이션을 사용하여 각각의 파라미터를 받아서 처리

        // 사용자 입력을 기반으로 초기 이야기 생성
        String initialPrompt = chatGPTService.prompt();
        String initialStory = chatGPTService.generateText(initialPrompt);
        System.out.println("initialStory: " + initialStory);


        // 선택지를 통해 스토리를 이어나가기 위해 현재 이야기를 업데이트(변수화)
        prevStory = initialStory;

        // 이야기와 선택지를 모델에 추가
        model.addAttribute("story", initialStory);

        // generate_story.html로 이동하여 렌더링하기 전에
        // Postman으로 체킹
        // initialStory 즉시 반환.
//        return initialStory;
        return "content";
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