package likelion_backend.OnSiL.domain.like.repository;

import likelion_backend.OnSiL.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserId(Long userId);
    List<Like> findByPostId(Long postId);
    void deleteByUserIdAndPostId(Long userId, Long postId);
}
