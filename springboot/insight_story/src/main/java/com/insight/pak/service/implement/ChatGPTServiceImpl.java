package com.insight.pak.service.implement;

import com.insight.pak.dto.ChatGPTRequest;
import com.insight.pak.dto.ChatGPTResponse;
import com.insight.pak.service.ChatGPTService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * ChatGPTServiceImpl
 * ChatGPTService 인터페이스의 구현체
 *
 * 세부 사항 메서드를 정리한 구현체
 * */

@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    private static final String API_KEY = "openAiApiKey";

    @Autowired
    private RestTemplate restTemplate;

    // 사용자가 입력한 apikey 저장
    @Override
    public void saveApiKey(HttpSession session, String apiKey) {
        session.setAttribute(API_KEY, apiKey);
    }

    // 사용자가 입력한 api key 가져옴
    @Override
    public String getApiKey(HttpSession session) {
        return (String) session.getAttribute(API_KEY);
    }

    @Override // API 호출하여 텍스트로 반환.
    public String generateText(String prompt) {
        /**
         * ChatGPTRequest 객체 생성.
         * model : 챗봇 모델의 상태를 나타내는 정보 파라미터
         * prompt : 챗봇에 입력될 문장이나 질문 파라미터
         * */
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);

        /**
         * ChatGPTResponse 객체 생성
         * apiURL : 요청을 보낼 API의 URL이 전달되는 파라미터
         * request : 요청 객체 파라미터
         * ChatGPTResponse.class : 요청을 보낸 후 받을 응답의 타입 클래스 파라미터
         * */
        ChatGPTResponse response = restTemplate.postForObject(apiURL, request, ChatGPTResponse.class);

        // 생성된 텍스트를 반환.
        return response
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }

    @Override // 첫 스토리 생성할 때 사용할 prompt
    public String Prompt(String prompt) {

        return String.format("#명령문\n" +
        "나는 텍스트 게임을 너와 해보려고 해.\n"+
        "내가 인물, 배경, 장르를 제시하면, 너는 내가 제시한 요소를 기반으로 소설을 만들어줘. 대신에, 소설을 다 만들면 안 돼. 중간 중간에 내가 선택해서 그에 따라 소설을 이어갈 수 있도록 끊고 선택지를 제공해줘.\n" +
        "\n" +
        "#예시\n" +
        "입력문:인물1: 홍설(이름), ESTP(MBTI), 조선시대 양반집의 딸로 태어났지만 집안이 역모죄 때문에 망해서 남장을 하며 광대로 살아가고 있음, 인물2: 김우빈(이름), ENFJ(MBTI), 조선시대 양반집의 잘생기고 모두가 좋아하는 남자, 인물3: 이율(이름), ISTP(MBTI), 조선시대 츤데레 면모가 있는 세자, 김우빈과 이율은 어렸을 때는 친한 친구였지만, 김우빈의 집안이 현대 왕을 몰아내려 하기에 지금은 서먹한 사이, 김우빈과 홍설은 저작거리에서 만나 친해진 사이, 홍설과 이율은 어렸을 때 잠깐 만났지만 지금은 서로 못 알아보는 상태, 배경: 조선시대, 장르: 로맨스\n" +
        "출력문:\n"+ 
        "{\n" +
        "\"story\": \"조선시대 한 양반가의 말도 안 되는 역사가 펼쳐지고 있었다. 홍설은 그 가운데서도 독특한 존재였다. 양반가의 딸로 태어나 집안의 역모죄 때문에 모든 것을 잃고 망하게 되었지만, 그녀는 남장을 하고 광대로 살아가고 있었다. 어느 날, 김우빈은 한 자리에 앉아 조용히 문서를 읽고 있었다. 그의 눈은 주위를 둘러보고 있었고, 미소가 그의 얼굴을 밝혀왔다. 한편으로는 그의 성격에 맞는 새로운 재미있는 일을 찾고 있었을 것이다. 김우빈이 책을 넘기는 동안, 이율이 입장했다. 세자의 차분한 표정은 어떤 일도 그를 극복할 수 있을 것처럼 보였다. 그는 어릴 적 김우빈과 함께한 추억을 회상했다. 그 순간, 홍설이 그들 사이를 통과하며 말이 없이 재미있는 모습을 연출했다. 김우빈과 이율은 그녀를 보고 어색한 미소를 지었고, 홍설은 자신의 끼를 보여주며 그들에게 다가갔다.\",\n" +
        "\"dialogue\": [\n" +
        "{\n" +
        "\"name\": \"김우빈\",\n" +
        "\"content\": \"어서 오세요, 홍설 씨!\"\n" +
        "},\n" +
        "{\n" +
        "\"name\": \"이율\",\n" +
        "\"content\": \"안녕하세요, 홍설 씨. 오랜만이네요.\"\n" +
        "},\n" +
        "{\n" +
        "\"name\": \"홍설\",\n" +
        "\"content\": \"안녕하세요, 두 분. 정말 오랜만이에요.\"\n" +
        "}\n" +
        "],\n" +
        "\"question\": \"이야기를 계속하려면, 홍설은 누구에게 더 가까이 다가갈까요?\",\n" +
        "\"choice1\": \"김우빈\",\n" +
        "\"choice2\": \"이율\",\n" +
        "\"choice3\": \"아무에게도 다가가지 않는다\"\n" +
        "}\n\n" +
        "\n" +
        "#제약조건\n" +
        "출력문의 양식 그대로 출력해줘.(JSON형식)\n" +
        "선택지를 골라 이야기를 만드는 과정이 10번이 되면, 이야기가 마무리되어야 해.\n" +
        "인물 간의 대화가 많았으면 해.\n" +
        "그리고 인물 간 대화할 때 '이름':'대화 내용'양식을 꼭 써줘.\n" +
        "매번 이야기가 새롭게 시작되는 게 아니라, 선택지를 선택하는 부분에서는 멈췄다가 내가 선택하는 것에 따라 이야기가 계속 이어져야 해.\n" +
        "\n"+prompt);
    }

    @Override // 사용자가 선택할 경우 이전 스토리와 선택지와 연관된 다음 스토리 생성하는 prompt
    public String continuePrompt(String prevStory, String choice) {
        return String.format(
            "#명령문\n" +
            "나는 텍스트 게임을 너와 해보려고 해.\n" + 
            "내가 이야기와 이야기 안에서의 선택지를 제시하면, 너는 내가 제시한 요소를 기반으로 소설을 이어서 만들어줘. 대신에, 소설을 다 만들면 안 돼. 중간 중간에 내가 선택해서 그에 따라 소설을 이어갈 수 있도록 끊고 선택지를 3개 제공해줘.\n" +
            "\n" +
            "#예시\n" + 
            "입력문:\n"+
            "{\n"+
            "\"story\": \"조선시대 한 양반가의 말도 안 되는 역사가 펼쳐지고 있었다. 홍설은 그 가운데서도 독특한 존재였다. 양반가의 딸로 태어나 집안의 역모죄 때문에 모든 것을 잃고 망하게 되었지만, 그녀는 남장을 하고 광대로 살아가고 있었다. 어느 날, 김우빈은 한 자리에 앉아 조용히 문서를 읽고 있었다. 그의 눈은 주위를 둘러보고 있었고, 미소가 그의 얼굴을 밝혀왔다. 한편으로는 그의 성격에 맞는 새로운 재미있는 일을 찾고 있었을 것이다. 김우빈이 책을 넘기는 동안, 이율이 입장했다. 세자의 차분한 표정은 어떤 일도 그를 극복할 수 있을 것처럼 보였다. 그는 어릴 적 김우빈과 함께한 추억을 회상했다. 그 순간, 홍설이 그들 사이를 통과하며 말이 없이 재미있는 모습을 연출했다. 김우빈과 이율은 그녀를 보고 어색한 미소를 지었고, 홍설은 자신의 끼를 보여주며 그들에게 다가갔다.\",\n" +
            "\"dialogue\": [\n" +
            "{\n" +
            "\"name\": \"김우빈\",\n" +
            "\"content\": \"어서 오세요, 홍설 씨!\"\n" +
            "},\n" +
            "{\n" +
            "\"name\": \"이율\",\n" +
            "\"content\": \"안녕하세요, 홍설 씨. 오랜만이네요.\"\n" +
            "},\n" +
            "{\n" +
            "\"name\": \"홍설\",\n" +
            "\"content\": \"안녕하세요, 두 분. 정말 오랜만이에요.\"\n" +
            "}\n" +
            "],\n" +
            "\"question\": \"이야기를 계속하려면, 홍설은 누구에게 더 가까이 다가갈까요?\",\n" +
            "\"choice1\": \"김우빈\",\n" +
            "\"choice2\": \"이율\",\n" +
            "\"choice3\": \"아무에게도 다가가지 않는다\"\n" +
            "}\"\n" +
            "\n" +
            "-> 2번 선택\n" +
            "\n" +
            "출력문:\n"+
            "{\n" +
            "\"story\": \"홍설은 마음을 다잡고 이율에게로 다가갔다. 그녀는 이율의 차분한 성격과 함께 있고 싶었다. 한편으로는 홍설의 선택에 기쁜 듯한 표정으로 이율은 가만히 있었다.\",\n" +
            "\"dialogue\": [\n" +
            "{\n" +
            "\"name\": \"이율\",\n" +
            "\"content\": \"홍설씨, 어떤 일로 오셨나요?\"\n" +
            "},\n" +
            "{\n" +
            "\"name\": \"홍설\",\n" +
            "\"content\": \"이율님, 제가 이렇게 다가와도 되나요?\"\n" +
            "},\n" +
            "{\n" +
            "\"name\": \"김우빈\",\n" +
            "\"content\": \"아, 홍설씨. 무슨 일 있으신가요?\"\n" +
            "},\n" +
            "],\n" +
            "\"question\": \"이야기를 계속하려면, 홍설의 선택은 어떻게 될까요?\",\n" +
            "\"choice1\": \"이율과 함께 시간을 보낸다.\",\n" +
            "\"choice2\": \"김우빈과 함께 시간을 보낸다.\",\n" +
            "\"choice3\": \"아무도 선택하지 않는다.\"\n" +
            "}\n" +
            "\n" +
            "#제약조건\n" +
            "출력문의 양식 그대로 출력해줘.(JSON형식)\n" +
            "선택지를 골라 이야기를 만드는 과정이 10번이 되면, 이야기가 마무리되어야 해.\n" +
            "인물 간의 대화가 많았으면 해.\n" +
            "그리고 인물 간 대화할 때 '이름':'대화 내용'양식을 꼭 써줘.\n" +
            "매번 이야기가 새롭게 시작되는 게 아니라, 선택지를 선택하는 부분에서는 멈췄다가 내가 선택하는 것에 따라 이야기가 계속 이어져야 해. \n" +
            "\n" +
            "#입력문\n" +
            "%s\n" +
            "-> %s번 선택", prevStory, choice);
    }
}
