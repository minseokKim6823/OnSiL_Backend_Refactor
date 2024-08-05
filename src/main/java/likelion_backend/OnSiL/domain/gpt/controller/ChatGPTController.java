package likelion_backend.OnSiL.domain.gpt.controller;


import io.swagger.v3.oas.annotations.Operation;
import likelion_backend.OnSiL.domain.gpt.dto.ChatCompletionDto;
import likelion_backend.OnSiL.domain.gpt.dto.ChatRequestMsgDto;
import likelion_backend.OnSiL.domain.gpt.service.ChatGPTService;
import likelion_backend.OnSiL.domain.healthnews.dto.HealthNewsResponseDto;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.repository.MemberJpaRepository;
import likelion_backend.OnSiL.domain.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/chatGpt")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;
    private final MemberJpaRepository memberJpaRepository;

    public ChatGPTController(ChatGPTService chatGPTService, MemberJpaRepository memberJpaRepository) {
        this.chatGPTService = chatGPTService;
        this.memberJpaRepository = memberJpaRepository;

    }

    @GetMapping("/modelList")
    public ResponseEntity<List<Map<String, Object>>> selectModelList() {
        List<Map<String, Object>> result = chatGPTService.modelList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/model/{modelName}")
    public ResponseEntity<Map<String, Object>> isValidModel(@PathVariable String modelName) {
        Map<String, Object> result = chatGPTService.isValidModel(modelName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @PostMapping("/legacyPrompt")
//    public ResponseEntity<Map<String, Object>> selectLegacyPrompt(@RequestBody ChatCompletionDto completionDto) {
//        log.debug("param :: " + completionDto.toString());
//        Map<String, Object> result = chatGPTService.legacyPrompt(completionDto);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @PostMapping("/prompt")
    public ResponseEntity<Map<String, Object>> selectPrompt(@RequestBody ChatCompletionDto chatCompletionDto) {
        log.debug("param :: " + chatCompletionDto.toString());
        Map<String, Object> result = chatGPTService.prompt(chatCompletionDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/prompt/yourdiet")
    @Operation(summary = "gpt-4로 음식 추천 받기 / 재영")
    public ResponseEntity<String> checkMyDiet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = ((UserDetails) authentication.getPrincipal()).getUsername();
        Optional<Member> member = memberJpaRepository.findByMemberId(memberId);

        String cond = member.get().getHealth_con();
        if (cond == null) {
            return new ResponseEntity<>("당신의 건강상태를 저장해주세요.", HttpStatus.NOT_FOUND);
        }
        ChatRequestMsgDto fixedMessage = ChatRequestMsgDto.builder()
                .role("user")
                .content(cond+" 같은 질병을 갖고 있는 사람에게 좋은 음식 추천해줘.")
                .build();

        ChatCompletionDto chatCompletionDto = ChatCompletionDto.builder()
                .model("gpt-4")
                .messages(Collections.singletonList(fixedMessage))
                .build();

        log.debug("param :: " + chatCompletionDto.toString());

        Map<String, Object> result = chatGPTService.prompt(chatCompletionDto);

        String content = extractContentFromResult(result);

        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    private String extractContentFromResult(Map<String, Object> result) {
        // result 맵에서 content 추출하는 로직
        List<Map<String, Object>> choices = (List<Map<String, Object>>) result.get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            if (message != null) {
                return (String) message.get("content");
            }
        }
        return null;
    }

}
