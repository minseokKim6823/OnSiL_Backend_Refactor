package likelion_backend.OnSiL.domain.board.repository;

import likelion_backend.OnSiL.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {  // 변경: Integer -> Long
    @Query("SELECT b FROM Board b ORDER BY b.recommend DESC")
    Page<Board> findByBoardRecommendPost(Pageable pageable);

    @Query("SELECT e FROM Board e WHERE COALESCE(:title, '') = '' OR e.title = :title")
    List<Board> findWithParams(@Param("title") String title);
}
