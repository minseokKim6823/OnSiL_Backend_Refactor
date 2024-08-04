package likelion_backend.OnSiL.domain.board_reply.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardReplyDto {
    private String writer;
    private String content;
    private Long boardId;  // 변경: int -> Long
}
