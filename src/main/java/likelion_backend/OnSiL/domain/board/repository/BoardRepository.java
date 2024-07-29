package likelion_backend.OnSiL.domain.board.repository;

import likelion_backend.OnSiL.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("SELECT b FROM Board b ORDER BY b.recommend DESC")
    Page<Board> findByBoardRecommendPost(Pageable pageable);
}
