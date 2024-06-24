package com.insight.pak;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.insight.pak.h2_database.Story;
import com.insight.pak.h2_database.StoryController;
import com.insight.pak.service.ChatGPTService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BasicMapping {

    @Autowired
    private ChatGPTService chatGPTService;
    @Autowired
    private StoryController storyController;

    @GetMapping("/")
    public String main(Model model) {
        List<Story> story_list = storyController.load_all_data();
        model.addAttribute("story_list", story_list);
        return "root_page";
    }

    @GetMapping("/content")
    public String content() {
        return "content"; // Content.html 파일명
    }
	
    // 사용자가 스토리를 클릭했을 때 상세 스토리 설명 페이지로 이동
	@GetMapping("/index/{id}")
    public String indexPage(Model model, @PathVariable("id") String id) {
        Story story = storyController.load_select_story(id);
        model.addAttribute("story", story);
        return "index"; // index.html 템플릿을 렌더링합니다.
    }
	
	@GetMapping("/api_key")
    public String apikey(HttpSession session, Model model) {
//        boolean isValid = chatGPTService.checkApiKey(session);
//
//        if (isValid) {
//            model.addAttribute("apiKeyStatus", "올바른 API KEY입니다.");
//        } else {
//            model.addAttribute("apiKeyStatus", "올바르지 않은 API KEY입니다.");
//        }
        return "api_key"; // api_key.html 파일명
    }

    @GetMapping("/saveApiKey")
    public String getSaveApiKey() {
        return "apikey_test";
    }

    @PostMapping("/saveApiKey")
    public String saveApiKey(@RequestParam("apiKey") String apiKey, HttpSession session, Model model) {
        try {
            chatGPTService.saveApiKey(session, apiKey);
            model.addAttribute("apiKey", apiKey);
            System.out.println("주입된 API_KEY:" + apiKey + "\n");
            return "apikey_test";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "유효하지 않은 API KEY 입니다. (서비스 사용 불가)");
            return "apikey_test"; // 예외 발생 시 서버측에 에러 메세지 표시
        } catch (Exception e) {
            model.addAttribute("error", "서버 에러 발생!! (서비스 사용 불가)");
            return "apikey_test";
        }
    }
}
