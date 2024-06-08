package com.insight.pak;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class basic_mapping {
	
	@GetMapping("/")
    public String main() {
        return "root_page";
    }
	
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
	
}