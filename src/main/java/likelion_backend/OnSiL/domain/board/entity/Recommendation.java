package likelion_backend.OnSiL.domain.board.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    private Board board; // `post_id`와 일치

    private String userEmail; // `user_email` 추가

}
