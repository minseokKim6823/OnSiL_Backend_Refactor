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
    public List<Like> getLikesByCommunityId(Long communityId) {
        return likeRepository.findByCommunityId(communityId);
    }

    public List<Like> getLikesByUserId(Long userId) {
        return likeRepository.findByUserId(userId);
    }

    // Delete
    public void deleteLike(Long communityId, Long userId) {
        likeRepository.deleteByCommunityIdAndUserId(communityId, userId);
    }
}
