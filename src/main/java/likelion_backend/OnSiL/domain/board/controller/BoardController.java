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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/onsil/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/write")
    @Operation(summary = "글작성 / 재영")
    public ResponseEntity<String> save(
            BoardRequestDTO boardDto) {

        boardService.save(boardDto);
        return ResponseEntity.ok("저장 성공");
    }

    @PutMapping("/update/{boardId}")
    @Operation(summary = "글수정 / 재영")
    public ResponseEntity<String> update(@RequestBody String boardData, @PathVariable int boardId,@RequestParam(value = "image", required = false) MultipartFile imageFile) throws JsonProcessingException {
        try {
            boardService.saveUpdate(boardData, boardId,imageFile);
            return ResponseEntity.ok("수정 성공");
        } catch (EntityNotFoundException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{boardId}")
    @Operation(summary = "글삭제 / 재영")
    public ResponseEntity<String> delete(@PathVariable int boardId) {
        boardService.delete(boardId);
        return ResponseEntity.ok("삭제 성공");
    }
    @GetMapping("/search/all")
    @Operation(summary = "글 검색 / 재영")
    public ResponseEntity<List<BoardResponseDTO>> search(@RequestParam(required = false) String title)
            throws JsonProcessingException {
        List<BoardResponseDTO> boardResponseDtoList = boardService.search(title);
        return new ResponseEntity<>(boardResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/recommend/list")
    @Operation(summary = "추천기준 검색 / 재영")
    public ResponseEntity<List<BoardResponseDTO>> boardrecommendList(
            @PageableDefault(page = 0, size = 7, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<BoardResponseDTO> popularBoardResponsePage = boardService.boardrecommendList(pageable).map(BoardResponseDTO::fromEntity);

        List<BoardResponseDTO> popularBoardResponseList = popularBoardResponsePage.getContent();
        return ResponseEntity.ok().body(popularBoardResponseList);
    }

//    @PostMapping("/recommend/up/{boardId}")
//    public ResponseEntity<String> increaseRecommend(@PathVariable int boardId) {
//        boardService.increaseRecommend(boardId);
//        return ResponseEntity.ok("추천 증가 성공");
//    }
//
//    @PostMapping("/recommend/down/{boardId}")
//    public ResponseEntity<String> decreaseRecommend(@PathVariable int boardId) {
//        boardService.decreaseRecommend(boardId);
//        return ResponseEntity.ok("추천 감소 성공");
//    }

    @PostMapping("/recommend/up/{boardId}")
    @Operation(summary = "추천업 / 재영")
    public ResponseEntity<String> increaseRecommend(@PathVariable int boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        if (currentUserEmail == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자를 찾을 수 없습니다.");
        }
        if (boardService.hasUserRecommended(boardId, currentUserEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 추천한 게시물입니다.");
        }

        boardService.increaseRecommend(boardId, currentUserEmail);
        return ResponseEntity.ok("추천 증가 성공");
    }

    @PostMapping("/recommend/down/{boardId}")
    @Operation(summary = "추천다운 / 재영")
    public ResponseEntity<String> decreaseRecommend(@PathVariable int boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        if (currentUserEmail == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자를 찾을 수 없습니다.");
        }
        if (!boardService.hasUserRecommended(boardId, currentUserEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("추천하지 않은 게시물입니다.");
        }

        boardService.decreaseRecommend(boardId, currentUserEmail);
        return ResponseEntity.ok("추천 감소 성공");
    }





}
