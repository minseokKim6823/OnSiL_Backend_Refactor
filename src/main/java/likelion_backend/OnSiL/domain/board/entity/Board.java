package likelion_backend.OnSiL.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int post_id;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int recommend;
    private String image;
    private String writer;

    public enum Category {
        SAN, JIL, CHIN
    }
}
