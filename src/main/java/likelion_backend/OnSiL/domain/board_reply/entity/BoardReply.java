package likelion_backend.OnSiL.domain.board_reply.entity;

import jakarta.persistence.*;
import likelion_backend.OnSiL.domain.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoardReply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11)
    private Long id;

    @Column(length = 30)
    private String content;

    @Column(length = 50)
    private String writer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private Board board;
}
