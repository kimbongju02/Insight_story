package com.insight.pak;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicMapping {
	
	@GetMapping("/")
    public String main() {
        return "root_page";
    }
	
}