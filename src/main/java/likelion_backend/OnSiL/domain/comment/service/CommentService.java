package likelion_backend.OnSiL.domain.comment.service;

import likelion_backend.OnSiL.domain.comment.dto.CommentRequest;
import likelion_backend.OnSiL.domain.comment.entity.Comment;
import likelion_backend.OnSiL.domain.comment.repository.CommentRepository;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.repository.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    public Comment createComment(CommentRequest commentRequest, String memberId) {
        // memberId를 통해 Member 엔티티를 조회
        Member member = memberJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // Comment 엔티티 생성 및 설정
        Comment comment = new Comment();
        comment.setPostId(commentRequest.getPostId());
        comment.setContent(commentRequest.getContent());
        comment.setNickname(member.getNickname());

        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, String memberId) {
        Member member = memberJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        Comment comment = commentRepository.findByCommentIdAndNickname(commentId, member.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        commentRepository.delete(comment);
    }
}
