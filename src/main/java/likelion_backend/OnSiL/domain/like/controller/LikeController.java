package likelion_backend.OnSiL.domain.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion_backend.OnSiL.domain.like.dto.LikeRequestDTO;
import likelion_backend.OnSiL.domain.like.service.LikeService;
import likelion_backend.OnSiL.domain.like.common.ResponseResult;
import likelion_backend.OnSiL.domain.like.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    @Operation(summary = "좋아요 추가 //준상")
    public ResponseResult<?> insert(@RequestBody @Valid LikeRequestDTO likeRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            likeService.createLike(likeRequestDTO.getPostId(), userDetails.getUsername());
            return ResponseResult.success(null);
        } catch (CustomException e) {
            return ResponseResult.failure(e.getMessage());
        } catch (Exception e) {
            return ResponseResult.failure("An unexpected error occurred");
        }
    }

    @DeleteMapping
    @Operation(summary = "좋아요 삭제 //준상")
    public ResponseResult<?> delete(@RequestBody @Valid LikeRequestDTO likeRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            likeService.deleteLikeByPostIdAndUsername(likeRequestDTO.getPostId(), userDetails.getUsername());
            return ResponseResult.success(null);
        } catch (CustomException e) {
            return ResponseResult.failure(e.getMessage());
        } catch (Exception e) {
            return ResponseResult.failure("An unexpected error occurred");
        }
    }
}
