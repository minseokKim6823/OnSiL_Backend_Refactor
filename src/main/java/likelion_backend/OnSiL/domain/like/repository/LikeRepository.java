package likelion_backend.OnSiL.domain.like.repository;

import likelion_backend.OnSiL.domain.board.entity.Board;
import likelion_backend.OnSiL.domain.like.entity.Like;
import likelion_backend.OnSiL.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByBoardAndMember(Board board, Member member);
    void deleteById(Long id); // like_id로 삭제하기 위해 메서드 추가
}
