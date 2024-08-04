package likelion_backend.OnSiL.domain.board_reply.service;

import jakarta.persistence.EntityNotFoundException;
import likelion_backend.OnSiL.domain.board.entity.Board;
import likelion_backend.OnSiL.domain.board.repository.BoardRepository;
import likelion_backend.OnSiL.domain.board_reply.dto.BoardReplyDto;
import likelion_backend.OnSiL.domain.board_reply.entity.BoardReply;
import likelion_backend.OnSiL.domain.board_reply.repository.BoardReplyJpaRepository;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardReplyService {

    private final BoardReplyJpaRepository boardReplyJpaRepository;
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public List<BoardReplyDto> findAll() {
        return boardReplyJpaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<BoardReplyDto> findById(Long id) {
        return boardReplyJpaRepository.findById(id).map(this::convertToDTO);
    }

    public BoardReplyDto save(BoardReplyDto boardReplyDto) {
        BoardReply boardReply = convertToEntity(boardReplyDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> member = memberService.findByMemberId(authentication.getName());
        String memberNickname = member.map(Member::getNickname).orElse("anonymousWriter");
        boardReply.setWriter(memberNickname);

        Optional<Board> board = boardRepository.findById(boardReplyDto.getBoardId());
        if (board.isPresent()) {
            boardReply.setBoard(board.get());
        } else {
            throw new EntityNotFoundException("Board with ID " + boardReplyDto.getBoardId() + " not found");
        }

        return convertToDTO(boardReplyJpaRepository.save(boardReply));
    }

    public void deleteById(Long id) {
        boardReplyJpaRepository.deleteById(id);
    }

    private BoardReplyDto convertToDTO(BoardReply boardReply) {
        BoardReplyDto boardReplyDto = new BoardReplyDto();
        boardReplyDto.setWriter(boardReply.getWriter());
        boardReplyDto.setContent(boardReply.getContent());
        boardReplyDto.setBoardId(boardReply.getBoard().getPostId());
        return boardReplyDto;
    }

    private BoardReply convertToEntity(BoardReplyDto boardReplyDto) {
        BoardReply boardReply = new BoardReply();
        boardReply.setWriter(boardReplyDto.getWriter());
        boardReply.setContent(boardReplyDto.getContent());

        if (boardReplyDto.getBoardId() != null) {  // 변경: int -> Long
            Optional<Board> board = boardRepository.findById(boardReplyDto.getBoardId());
            board.ifPresent(boardReply::setBoard);
        }

        return boardReply;
    }
}
