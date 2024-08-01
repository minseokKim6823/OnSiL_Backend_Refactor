package likelion_backend.OnSiL.domain.comment.service;

import likelion_backend.OnSiL.domain.comment.entity.Comment;
import likelion_backend.OnSiL.domain.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // Create
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Read
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    // Update
    public Comment updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(commentDetails.getContent());
        return commentRepository.save(comment);
    }

    // Delete
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }
}
