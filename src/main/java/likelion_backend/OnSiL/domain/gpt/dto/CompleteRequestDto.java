package likelion_backend.OnSiL.domain.gpt.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteRequestDto {
    private String model;
    private List<ChatRequestMsgDto> messages;
}
