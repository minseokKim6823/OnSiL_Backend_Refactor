package likelion_backend.OnSiL.domain.like.controller;

import likelion_backend.OnSiL.domain.like.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/{postId}")
    public String createLike(@PathVariable int postId, @AuthenticationPrincipal UserDetails userDetails) {
        likeService.createLike(postId, userDetails.getUsername());
        return "Like added";
    }

    @DeleteMapping("/{likeId}")
    public String deleteLike(@PathVariable Long likeId) {
        likeService.deleteLike(likeId);
        return "Like removed";
    }
}
