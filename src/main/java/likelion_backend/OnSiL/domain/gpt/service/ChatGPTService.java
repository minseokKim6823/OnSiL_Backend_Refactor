package likelion_backend.OnSiL.domain.gpt.service;



import likelion_backend.OnSiL.domain.gpt.dto.ChatCompletionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface ChatGPTService {

    List<Map<String, Object>> modelList();

    Map<String, Object> isValidModel(String modelName);

    Map<String, Object> legacyPrompt(ChatCompletionDto completionDto);

    Map<String, Object> prompt(ChatCompletionDto chatCompletionDto);
}