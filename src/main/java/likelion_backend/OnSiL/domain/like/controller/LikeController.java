package likelion_backend.OnSiL.domain.like.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "좋아요 추가 //준상")
    public String createLike(@PathVariable int postId, @AuthenticationPrincipal UserDetails userDetails) {
        likeService.createLike(postId, userDetails.getUsername());
        return "Like added";
    }

    @DeleteMapping("/{likeId}")
    @Operation(summary = "좋아요 삭제 //준상")
    public String deleteLike(@PathVariable Long likeId) {
        likeService.deleteLike(likeId);
        return "Like removed";
    }
}
