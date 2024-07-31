package likelion_backend.OnSiL.domain.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recomId; // `Integer` 타입으로 수정

    private String userId; // `user_id`와 일치
    private Integer boardId; // `board_id`와 일치
    private String userEmail; // `user_email` 추가
}
