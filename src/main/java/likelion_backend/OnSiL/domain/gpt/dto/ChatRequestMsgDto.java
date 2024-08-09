package likelion_backend.OnSiL.domain.gpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Please explain the class!!
 *
 * @author : lee
 * @fileName : ChatRequestMsgDto
 * @since : 1/18/24
 */
@Getter
@Setter
@ToString
public class ChatRequestMsgDto {

    private String role;

    private String content;

    @Builder
    public ChatRequestMsgDto(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
