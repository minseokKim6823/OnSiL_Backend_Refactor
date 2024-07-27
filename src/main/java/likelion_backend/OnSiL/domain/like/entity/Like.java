package likelion_backend.OnSiL.domain.like.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "likes") // 테이블 이름 변경
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    private Long communityId; // 좋아요가 달린 게시글 ID

    private Long userId; // 좋아요 누른 사용자 ID

    // Getters and Setters

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
