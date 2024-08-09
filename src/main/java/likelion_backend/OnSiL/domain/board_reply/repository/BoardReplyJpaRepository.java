package likelion_backend.OnSiL.domain.board_reply.repository;

import likelion_backend.OnSiL.domain.board_reply.entity.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardReplyJpaRepository extends JpaRepository<BoardReply, Long> {
}
