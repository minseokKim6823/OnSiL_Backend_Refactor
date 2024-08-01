package likelion_backend.OnSiL.domain.like.service;

import likelion_backend.OnSiL.domain.like.entity.Like;
import likelion_backend.OnSiL.domain.like.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    // Create
    public Like createLike(Like like) {
        return likeRepository.save(like);
    }

    // Read
    public List<Like> getLikesByUserId(Long userId) {
        return likeRepository.findByUserId(userId);
    }

    public List<Like> getLikesByPostId(Long postId) {
        return likeRepository.findByPostId(postId);
    }

    public Like getLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElseThrow(() -> new RuntimeException("Like not found"));
    }

    // Delete
    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }

    public void deleteLikeByUserIdAndPostId(Long userId, Long postId) {
        likeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
