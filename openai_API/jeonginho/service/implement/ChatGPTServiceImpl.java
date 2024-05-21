package jeonginho.chatgptapi.service.implement;

import jeonginho.chatgptapi.dto.ChatGPTRequest;
import jeonginho.chatgptapi.dto.ChatGPTResponse;
import jeonginho.chatgptapi.service.ChatGPTService;
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

    @Autowired
    private RestTemplate restTemplate;

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

    @Override // 추가
    public String prompt(String background, String main, String sub1, String sub2, String setting) {
//        return String.format("배경은 %s이고 등장인물은 주인공(%s)과 %s,%s이 나오는 %s장르의 소설을 만들어줘 대화도 있으면 좋겠어."
//                + " 추가적으로 긴 내용의 소설을 부탁할게. \n"
//                + "또한 끝에 사용자에게 스토리의 방향성을 제시하는 선택지(3개)도 넣어줘. \n"
//                + "조건을 정리해줄게.\n" +
//                "1. 이 조건은 앞으로 쭉 이어진다.\n" +
//                "2. 선택지는 번호와 함께 내용을 보여줘야 한다. \n" +
//                "3. 처음에 선택지를 고를 때부터 카운트를 시작한다\n" +
//                "4. 카운트가 5가 되면 이야기를 완결낸다. \n" +
//                "그럼 이제 이야기를 만들어봐\n\n###\n\n", background, main, sub1, sub2, setting);
        return String.format("[조건]\n" +
                "내가 배경, 인물, 장르를 제시하면, 너는 내가 제시한 요소를 기반으로 소설을 만들어줘. \n" +
                "대신에, 소설을 다 만들면 안 돼. 그리고 인물간 대화도 필요해. 또한 중간 중간에 내가 선택해서 그에 따라 소설을 이어갈 수 \n" +
                "있도록 끊고 선택지(3개)를 제공해줘. \n" +
                "\n" +
                "아래는 예시야 참고해서 맨 마지막 설정사항들을 이용하여 스토리를 만들어봐" +
                "[예제]\n" +
                "{여자 아이, 중세시대, 로맨스 판타지}\n" +
                "{중세시대 무렵, 어느 한 부자집 여자아이는 부모님의 사랑을 듬뿍 받으며 잘 살고 있었다. \n" +
                "그 여자아이에게는 한 명의 남자 소꿉 친구가 있었고, 그 소꿉친구는 여자아이를 좋아하고 있었다.\n" +
                "하지만, 여자아이는 나라의 왕자를 좋아하고 있었다. 어느날, 여자아이는 파티에 가게 되었는데,\n" +
                "함께 갈 사람을 고르려고 한다.}\n" +
                "{여자 아이는 누구에게 초대장을 보낼까? \n" +
                "1: 소꿉친구 2: 왕자 3: 보내지 않고 혼자 파티에 간다}\n" +
                "\n" +
                "설정사항 - 배경 : %s, 주인공 : %s, 등장인물1 : %s, 등장인물2 : %s, 장르 : %s", background, main, sub1, sub2, setting);
    }

    @Override // 추가
    public String continuePrompt(String prevStory, String choice) {
        return String.format("이전 스토리는 %s이고 이전 스토리에 대한 선택지로는 %s번을 선택할게. 선택한 선택지와 이전 스토리를 기반으로"
                + "소설을 이어서 작성해줘. 대신 자극적인 내용도 있어야 해. 또한 등장인물간 대화는 필수야."
                + "추가적으로 끝에 마찬가지로 사용자에게 스토리의 방향성을 제시하는 선택지(3개도) 꼭 넣어줘. \n" +
                "- 조건을 정리해줄게.\n" +
                "1. 선택지는 번호와 함께 내용을 보여줘야 한다. \n", prevStory, choice);
    }
}
