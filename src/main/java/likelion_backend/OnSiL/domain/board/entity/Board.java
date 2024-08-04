package likelion_backend.OnSiL.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;  // 변경: Integer -> Long

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int recommend;
    private String image;
    private String writer;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations;

    public enum Category {
        SAN, JIL, CHIN
    }
}
