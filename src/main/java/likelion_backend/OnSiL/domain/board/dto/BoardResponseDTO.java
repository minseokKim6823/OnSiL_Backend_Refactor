package likelion_backend.OnSiL.domain.board.dto;

import likelion_backend.OnSiL.domain.board.entity.Board;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO {
    private Long postId;
    private String title;
    private String content;
    private Board.Category category;
    private int recommend;
    private String image;
    private String writerNickname;  // 작성자의 닉네임 필드 추가

    // 엔티티에서 DTO로 변환하는 정적 메소드
    public static BoardResponseDTO fromEntity(Board board) {
        return BoardResponseDTO.builder()
                .postId(board.getPostId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .recommend(board.getRecommend())
                .image(board.getImage())
                .writerNickname(board.getWriter().getNickname())  // 작성자의 닉네임 설정
                .build();
    }
}