package proj.AIssue.domain.gpt.service;

import org.springframework.stereotype.Service;
import proj.AIssue.domain.gpt.dto.ChatCompletionDTO;
import proj.AIssue.domain.gpt.dto.CompletionDTO;

import java.util.List;
import java.util.Map;

@Service
public interface ChatGPTService {

    // ChatGPT 모델 리스트 조회
    List<Map<String, Object>> modelList();

    // 유효한 모델 체크
    Map<String, Object> isValidModel(String modelName);

    // 레거시 모델 요청
    Map<String, Object> legacyPrompt(CompletionDTO completionDto);

    // 최신 모델 요청
    Map<String, Object> prompt(ChatCompletionDTO chatCompletionDto);
}