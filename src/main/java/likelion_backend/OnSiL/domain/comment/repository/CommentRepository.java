package likelion_backend.OnSiL.domain.comment.repository;

import likelion_backend.OnSiL.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByCommentIdAndNickname(Long commentId, String nickname);
    Optional<Comment> findByCommentIdAndNickname(Long commentId, String nickname);
}
