package likelion_backend.OnSiL.domain.comment.repository;

import likelion_backend.OnSiL.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommunityId(Long communityId);
}
