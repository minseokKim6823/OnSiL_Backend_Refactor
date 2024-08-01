package likelion_backend.OnSiL.domain.comment.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Long postId; // 댓글이 달린 게시글 ID

    private String userId; // 댓글 작성자 ID

    @Lob
    private String content; // 댓글 내용

    private LocalDateTime time = LocalDateTime.now(); // 댓글 작성 시간

    // Getters and Setters

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
