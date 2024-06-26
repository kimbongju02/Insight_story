package com.insight.pak;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.pak.dto.StoryRequest;
import com.insight.pak.dto.StoryResponse;
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
    
    @GetMapping("/testpage")
    public String testpage() {
        return "testpage"; // Content.html 파일명
    }
	
    // 사용자가 스토리를 클릭했을 때 상세 스토리 설명 페이지로 이동
	@GetMapping("/index/{id}")
    public String indexPage(Model model, @PathVariable("id") String id) {
        Story story = storyController.load_select_story(id);
        model.addAttribute("story", story);
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
 
    @ResponseBody
    @PostMapping("/saveApiKey")
    public String saveApiKey(@RequestBody String api_key, HttpSession session, Model model) {
        try{
            chatGPTService.saveApiKey(session, api_key);
            return "s";
        }catch(Exception e){
            return "e";
        }
    }
    
    @ResponseBody
    @GetMapping("/load/image/story/{id}")
    public String load_image_story( @PathVariable("id") String id){
    	Story story = storyController.load_select_story(id);
    	return story.getImage();
    }
}
