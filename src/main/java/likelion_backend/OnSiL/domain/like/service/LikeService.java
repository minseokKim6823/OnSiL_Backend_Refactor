package likelion_backend.OnSiL.domain.like.service;

import likelion_backend.OnSiL.domain.board.entity.Board;
import likelion_backend.OnSiL.domain.board.repository.BoardRepository;
import likelion_backend.OnSiL.domain.like.entity.Like;
import likelion_backend.OnSiL.domain.like.repository.LikeRepository;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.repository.MemberJpaRepository;
import likelion_backend.OnSiL.domain.like.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberJpaRepository memberRepository;

    @Transactional
    public void createLike(long postId, String memberId) {
        Board board = boardRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Invalid post ID"));
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException("Invalid member ID"));

        boolean exists = likeRepository.existsByBoardAndMember(board, member);
        if (exists) {
            throw new CustomException("Already liked by this user");
        }

        Like like = new Like();
        like.setBoard(board);
        like.setMember(member);
        likeRepository.save(like);
    }

    @Transactional
    public void deleteLikeByPostIdAndUsername(Long postId, String username) {
        Board board = boardRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Invalid post ID"));
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new CustomException("Invalid member ID"));

        Like like = likeRepository.findByBoardAndMember(board, member)
                .orElseThrow(() -> new CustomException("Like not found"));
        likeRepository.delete(like);
    }
}
