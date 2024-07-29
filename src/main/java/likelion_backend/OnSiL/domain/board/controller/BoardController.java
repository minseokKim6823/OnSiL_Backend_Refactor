package likelion_backend.OnSiL.domain.board.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import likelion_backend.OnSiL.domain.board.dto.BoardRequestDTO;
import likelion_backend.OnSiL.domain.board.dto.BoardResponseDTO;
import likelion_backend.OnSiL.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/onsil/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/write")
    @Operation(summary = "글 생성")
    public ResponseEntity<String> save(@RequestBody BoardRequestDTO boardDto) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //사용자 정보저장
        String currentUserEmail = authentication.getName(); // 현재 사용자의 이메일 가져오기
        System.out.println(authentication);
        System.out.println("currentUserEmail"+currentUserEmail);
        // 현재 사용자를 찾을 수 없는 경우 Forbidden 반환
        if (currentUserEmail   == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자를 찾을 수 없습니다.");
        }
        boardService.save(boardDto);
        return ResponseEntity.ok("저장 성공");
    }

    @PutMapping("/update/{boardId}")
    @Operation(summary = "게시물 수정")
    public ResponseEntity<String> update(@RequestBody String boardData, @PathVariable int boardId) throws JsonProcessingException {
        try {
            boardService.saveUpdate(boardData, boardId);
            return ResponseEntity.ok("수정 성공");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{boardId}")
    @Operation(summary = "게시물 삭제")
    public ResponseEntity<String> delete(@PathVariable int boardId) {
        boardService.delete(boardId);
        return ResponseEntity.ok("삭제 성공");
    }

    @GetMapping("/recommend/list")
    @Operation(summary = "추천 기준 인기 게시물")
    public ResponseEntity<List<BoardResponseDTO>> boardrecommendList(
            @PageableDefault(page = 0, size = 7, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<BoardResponseDTO> popularBoardResponsePage = boardService.boardrecommendList(pageable).map(BoardResponseDTO::fromEntity);

        List<BoardResponseDTO> popularBoardResponseList = popularBoardResponsePage.getContent();
        return ResponseEntity.ok().body(popularBoardResponseList);
    }

    @PostMapping("/recommend/up/{boardId}")
    @Operation(summary = "게시물 추천 증가")
    public ResponseEntity<String> increaseRecommend(@PathVariable int boardId) {
        boardService.increaseRecommend(boardId);
        return ResponseEntity.ok("추천 증가 성공");
    }

    @PostMapping("/recommend/down/{boardId}")
    @Operation(summary = "게시물 추천 감소")
    public ResponseEntity<String> decreaseRecommend(@PathVariable int boardId) {
        boardService.decreaseRecommend(boardId);
        return ResponseEntity.ok("추천 감소 성공");
    }





}
