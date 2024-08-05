package likelion_backend.OnSiL.domain.board.entity;

import jakarta.persistence.*;
import likelion_backend.OnSiL.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private long postId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "recommend")
    private int recommend;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;  // 작성자 정보를 Member 객체로 변경

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations;

    public enum Category {
        SAN, // 산책
        JIL, // 질병
        CHIN // 커뮤니티
    }
}
