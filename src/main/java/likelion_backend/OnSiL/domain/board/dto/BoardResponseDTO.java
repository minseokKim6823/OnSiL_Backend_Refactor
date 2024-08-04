package likelion_backend.OnSiL.domain.board.dto;

import likelion_backend.OnSiL.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO {
    private int postId;
    private String title;
    private String content;
    private Board.Category category;
    private int recommend;
    private String image;
    private String writer;

    // 엔티티에서 DTO로 변환하는 정적 메소드
    public static BoardResponseDTO fromEntity(Board board) {
        return BoardResponseDTO.builder()
                .postId(board.getPostId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .recommend(board.getRecommend())
                .writer(board.getWriter())
                .image(board.getImage())
                .build();
    }
}
