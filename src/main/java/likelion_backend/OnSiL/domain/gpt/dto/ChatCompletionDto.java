package likelion_backend.OnSiL.domain.gpt.dto;

import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatCompletionDto {

    private String model;

    private List<ChatRequestMsgDto> messages;

    @Builder
    public ChatCompletionDto(String model, List<ChatRequestMsgDto> messages) {
        this.model = model;
        this.messages = messages;
    }
}
