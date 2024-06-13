package com.insight.pak.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * ChatGPTConfig
 * 설정 정보
 *
 * openAiKey = your-secret-api-key
 * Authorizayion에 apikey값을 주입.
 * */

@Configuration
public class ChatGPTConfig {

    private final HttpSession httpSession;

    @Autowired
    public ChatGPTConfig(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(((request, body, execution) -> {
            String apiKey = (String) httpSession.getAttribute("openAiApiKey");
            if (apiKey != null) {
                request.getHeaders().add("Authorization", "Bearer " + apiKey);
            }
            return execution.execute(request, body);
        }));

        return restTemplate;
    }
}
