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

    @GetMapping("/index") //GET 요청에 대한 핸들러 메서드.
    public String getIndex() {
        return "index";
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
