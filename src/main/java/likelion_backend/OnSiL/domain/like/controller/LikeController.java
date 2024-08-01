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
    @GetMapping("/user/{userId}")
    public List<Like> getLikesByUserId(@PathVariable Long userId) {
        return likeService.getLikesByUserId(userId);
    }

    @GetMapping("/post/{postId}")
    public List<Like> getLikesByPostId(@PathVariable Long postId) {
        return likeService.getLikesByPostId(postId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Like> getLikeById(@PathVariable Long id) {
        Like like = likeService.getLikeById(id);
        return ResponseEntity.ok(like);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<Void> deleteLikeByUserIdAndPostId(@PathVariable Long userId, @PathVariable Long postId) {
        likeService.deleteLikeByUserIdAndPostId(userId, postId);
        return ResponseEntity.noContent().build();
    }
}
