package com.insight.pak;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/index")
    public String indexPage(@RequestParam String story_num) {
        return "/index?story_num=%story_num" +story_num; // index.html 템플릿을 렌더링합니다.
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
