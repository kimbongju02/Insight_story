package jeonginho.chatgptapi.service;

public interface ChatGPTService {
    String generateText(String prompt);
    String prompt(String background, String main, String sub1, String sub2, String setting);
    String continuePrompt(String prevStory, String choice);
}
