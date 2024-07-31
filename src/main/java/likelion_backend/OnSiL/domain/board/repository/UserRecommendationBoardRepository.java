package likelion_backend.OnSiL.domain.board.repository;

import likelion_backend.OnSiL.domain.board.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecommendationBoardRepository extends JpaRepository<Recommendation, Integer> {
    boolean existsByUserIdAndBoardId(String userId, Integer boardId); // Integer로 수정

    Recommendation findByUserIdAndBoardId(String userId, Integer boardId); // Integer로 수정
}
