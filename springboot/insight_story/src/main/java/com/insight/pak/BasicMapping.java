package com.insight.pak;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicMapping {

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
}
