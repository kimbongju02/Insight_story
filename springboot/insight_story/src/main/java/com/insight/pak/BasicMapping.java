package com.insight.pak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.insight.pak.service.ChatGPTService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BasicMapping {

    @Autowired
    private ChatGPTService chatGPTService;

    // rootpage 조회

    @GetMapping("/")
    public String main() {
        return "root_page";
    }
    // Index 조회

    @GetMapping("/content")
    public String content() {
        return "content"; // Content.html 파일명
    }
	
	@GetMapping("/index")
    public String indexPage(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("keyword", keyword);
        }
        return "index"; // index.html 템플릿을 렌더링합니다.
    }
	
	@GetMapping("/api_key")
    public String apikey() {
        return "api_key"; // Content.html 파일명
    }

    @GetMapping("/saveApiKey")
    public String getSaveApiKey() {
        return "apikey_test";
    }

    @PostMapping("/saveApiKey")
    public String saveApiKey(@RequestParam("apiKey") String apiKey, HttpSession session, Model model) {
        chatGPTService.saveApiKey(session, apiKey);
        model.addAttribute("apiKey", apiKey);
        return "testpage";
    }
}
