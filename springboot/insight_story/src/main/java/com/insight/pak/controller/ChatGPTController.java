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
    public String getGenerateStory() {
        return "testpage";
    }

//    @PostMapping(value = "/content")
//    public String postGenerateStory(@RequestParam Map<String, String> requestParams, Model model) {
//
//        String synopsis = "조선시대 양반집의 딸인 홍설은 역모죄로 인해 남장 광대로 살아가고 있다. 어느 날, 홍설은 조선시대의 잘생긴 남자 김우빈을 만나 친해진다. "
//                + "그러나 홍설의 집안은 김우빈의 집안을 현대 왕을 몰아내려 하기 때문에 두 사람은 서먹한 사이다. 한편, 김우빈과 홍설은 저작거리에서 만나 친해짐과 동시에 홍설과 이율은 "
//                + "어렸을 때 만났지만 서로 못 알아보는 상태이다. 이제 홍설은 김우빈과 이율 중 한 명과 파트너가 되어 파티에 참석하려 한다.";
//
//        // 대화내용
//        Map<String, String> dialogue = new HashMap<>();
//        dialogue.put("김우빈", "홍설아, 넌 왜 항상 저 모습으로 다니냐?");
//        dialogue.put("홍설1", "지금의 모습이 날 편하게 해. 그래서 너한테도 평범하게 다가갈 수 있어.");
//        dialogue.put("김우빈1", "하지만 난 그런 네 모습을 좋아하지 않아. 너에게 포근한 듯 따스한 여유를 느끼고 싶어.");
//        dialogue.put("이율1", "홍설아, 네가 그런 모습을 하게 된 이유가 뭐야?");
//        dialogue.put("홍설2", "우리 집안이 양반집으로 몰리면서 망한 거야. 이렇게 다니면 더이상 저주 받지 않는데.");
//        dialogue.put("이율2", "나도 네가 김우빈이 좋아하는 여자라는 걸 알고 있어. 정말 너무 아쉽지만.. 난 응원할게.");
//
//        // 선택지
//        String selectText = "홍설을 도와줄 사람을 선택해주세요. 김우빈, 이율";
//
//        Map<String, String> choices = new HashMap<>();
//        choices.put("첫번째", "김우빈");
//        choices.put("두번째", "이율");
//
//        // 전체 이야기 Map
//        Map<String, Object> story = new HashMap<>();
//        story.put("줄거리", synopsis);
//        story.put("대화내용", dialogue);
//        story.put("선택지제시", selectText);
//        story.put("선택지", choices);
//
//        model.addAttribute("story", story);
//
//        System.out.println(story);
//
//        return "content";
//    }

    @PostMapping(value = "/testpage")
    public String postGenerateStory(@RequestParam Map<String, String> requestParams, Model model) throws JsonProcessingException {
        // @RequestParam 어노테이션을 사용하여 각각의 파라미터를 받아서 처리
        String[] characterKeys = {"mainCharacter", "supportingCharacter1", "supportingCharacter2"};
        String[] characters = new String[characterKeys.length];

        for (int i = 0; i < characterKeys.length; i++) {
            characters[i] = requestParams.get(characterKeys[i]);
        }

        // 사용자 입력을 기반으로 초기 이야기 생성
        String initialPrompt = chatGPTService.horrorPrompt(characters);
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

        // 이야기와 선택지를 모델에 추가
//        model.addAttribute("story", initialStory);

        // JSON 데이터를 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        StoryResponse storyResponse = objectMapper.readValue(initialStory, StoryResponse.class);

        // 모델에 데이터 추가
        model.addAttribute("story", storyResponse.getStory());
        model.addAttribute("question", storyResponse.getQuestion());
        model.addAttribute("choice1", storyResponse.getChoice1());
        model.addAttribute("choice2", storyResponse.getChoice2());
        model.addAttribute("choice3", storyResponse.getChoice3());
        model.addAttribute("dialogues", storyResponse.getDialogues().entrySet());

        return "testpage";
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