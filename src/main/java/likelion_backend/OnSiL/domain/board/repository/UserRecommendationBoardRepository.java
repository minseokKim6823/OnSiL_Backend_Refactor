package likelion_backend.OnSiL.domain.board.repository;

import likelion_backend.OnSiL.domain.board.entity.Board;
import likelion_backend.OnSiL.domain.board.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecommendationBoardRepository extends JpaRepository<Recommendation, Long> {
    boolean existsByUserIdAndBoard(String userId, Board board);  // Board 객체를 사용

    Recommendation findByUserIdAndBoard(String userId, Board board);  // Board 객체를 사용
}
