package com.insight.pak;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicMapping{
	
	@GetMapping("/")
    public String main() {
        return "root_page";
    }
	
	@GetMapping("/content")
    public String content() {
        return "Content"; // Content.html 파일명
    }
	
	@GetMapping("/index")
    public String test() {
        return "index"; // Content.html 파일명
    }
}