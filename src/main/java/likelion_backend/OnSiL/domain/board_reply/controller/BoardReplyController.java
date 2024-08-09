package likelion_backend.OnSiL.domain.board_reply.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion_backend.OnSiL.domain.board_reply.dto.BoardReplyDto;
import likelion_backend.OnSiL.domain.board_reply.service.BoardReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/board-reply")
public class BoardReplyController {

    @Autowired
    private BoardReplyService boardReplyService;

    @GetMapping
    @Operation(summary = "게시판 댓글 전체 조회 // 준상")
    public List<BoardReplyDto> getAllBoardReplies() {
        return boardReplyService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "게시판 댓글 조회 // 준상")
    public ResponseEntity<BoardReplyDto> getBoardReplyById(@PathVariable Long id) {
        Optional<BoardReplyDto> boardReplyDto = boardReplyService.findById(id);
        if (boardReplyDto.isPresent()) {
            return ResponseEntity.ok(boardReplyDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{boardId}")
    @Operation(summary = "게시판 댓글 작성 // 준상")
    public ResponseEntity<BoardReplyDto> createBoardReply(@PathVariable Long boardId, @RequestBody BoardReplyDto boardReplyDto) {  // 변경: int -> Long
        boardReplyDto.setBoardId(boardId);
        BoardReplyDto savedBoardReply = boardReplyService.save(boardReplyDto);
        return ResponseEntity.ok(savedBoardReply);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시판 댓글 삭제 // 준상")
    public ResponseEntity<Void> deleteBoardReply(@PathVariable Long id) {
        if (boardReplyService.findById(id).isPresent()) {
            boardReplyService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
