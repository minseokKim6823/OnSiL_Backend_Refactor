package likelion_backend.OnSiL.domain.like.controller;

import likelion_backend.OnSiL.domain.like.entity.Like;
import likelion_backend.OnSiL.domain.like.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    // Create
    @PostMapping
    public Like createLike(@RequestBody Like like) {
        return likeService.createLike(like);
    }

    // Read
    @GetMapping("/community/{communityId}")
    public List<Like> getLikesByCommunityId(@PathVariable Long communityId) {
        return likeService.getLikesByCommunityId(communityId);
    }

    @GetMapping("/user/{userId}")
    public List<Like> getLikesByUserId(@PathVariable Long userId) {
        return likeService.getLikesByUserId(userId);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<Void> deleteLike(@RequestParam Long communityId, @RequestParam Long userId) {
        likeService.deleteLike(communityId, userId);
        return ResponseEntity.noContent().build();
    }
}
