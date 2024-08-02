package likelion_backend.OnSiL.domain.board.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import likelion_backend.OnSiL.domain.board.dto.BoardRequestDTO;
import likelion_backend.OnSiL.domain.board.repository.MemberRepository;

import likelion_backend.OnSiL.domain.board.dto.BoardResponseDTO;
import likelion_backend.OnSiL.domain.board.entity.Board;
import likelion_backend.OnSiL.domain.board.entity.Recommendation;
import likelion_backend.OnSiL.domain.board.repository.BoardRepository;
import likelion_backend.OnSiL.domain.board.repository.UserRecommendationBoardRepository;
import likelion_backend.OnSiL.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRecommendationBoardRepository userRecommendationBoardRepository;
    private final MemberRepository memberRepository;
    private final S3FileUploadService s3FileUploadService;

    /*@Transactional
    public void save(BoardRequestDTO boardDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                throw new IllegalStateException("인증 정보가 없습니다.");
            }
            String currentUserEmail = authentication.getName();
            log.info("현재 사용자의 이메일: {}", currentUserEmail);
            Board board = new Board();
            board.setWriter(currentUserEmail);
            board.setTitle(boardDTO.getTitle());
            board.setContent(boardDTO.getContent());
            board.setImage(boardDTO.getImage());
            board.setCategory(boardDTO.getCategory());
            board.setRecommend(0);
            boardRepository.save(board);
            log.info("게시물 저장 성공: {}", board);
        } catch (Exception e) {
            log.error("게시물 저장 실패", e);
            throw new RuntimeException("게시물 저장 중 오류가 발생", e);
        }
    } */
    @Transactional
    public void save(BoardRequestDTO boardDTO, MultipartFile imageFile) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                throw new IllegalStateException("인증 정보가 없습니다.");
            }
            String currentUserEmail = authentication.getName();
            log.info("현재 사용자의 이메일: {}", currentUserEmail);

            // 사용자 엔티티 조회
            Optional<Member> optionalMember = memberRepository.findByMemberId(currentUserEmail);
            if (optionalMember.isEmpty()) {
                throw new IllegalStateException("사용자 정보를 찾을 수 없습니다.");
            }
            Member member = optionalMember.get();
            String nickname = member.getNickname();
            log.info("현재 사용자의 닉네임: {}", nickname);

            String imageUrl = null;
            if (imageFile != null && !imageFile.isEmpty()) {
                imageUrl = s3FileUploadService.uploadFile(imageFile);
            }

            Board board = new Board();
            board.setImage(imageUrl);
            board.setWriter(nickname);
            board.setTitle(boardDTO.getTitle());
            board.setContent(boardDTO.getContent());
            board.setCategory(boardDTO.getCategory());
            board.setRecommend(0);
            boardRepository.save(board);
            log.info("게시물 저장 성공: {}", board);
        } catch (Exception e) {
            log.error("게시물 저장 실패", e);
            throw new RuntimeException("게시물 저장 중 오류가 발생", e);
        }
    }

    public void saveUpdate(String boardDTO, int boardId, MultipartFile imageFile) throws  IOException {
        // 기존 게시물을 확인하여 존재하는지 확인
        Board existingBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        // ObjectMapper를 사용하여 JSON 문자열을 BoardDto 객체로 변환
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = s3FileUploadService.uploadFile(imageFile);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        BoardRequestDTO updatedBoardDto = objectMapper.readValue(boardDTO, BoardRequestDTO.class);
        // 기존 게시물의 필드들을 업데이트
        existingBoard.setTitle(updatedBoardDto.getTitle());
        existingBoard.setContent(updatedBoardDto.getContent());
        existingBoard.setCategory(updatedBoardDto.getCategory());
        if (imageUrl != null) {
            existingBoard.setImage(imageUrl); // 이미지 URL 업데이트
        }

        boardRepository.save(existingBoard);
    }
    @Transactional
    public List<BoardResponseDTO> search(String title) throws JsonProcessingException {
        List<Board> boardEntities = boardRepository.findWithParams(title);
        List<BoardResponseDTO> boardResponseDtoList = new ArrayList<>();
        for (Board board : boardEntities) {
            boardResponseDtoList.add(BoardResponseDTO.fromEntity(board));
        }
        return boardResponseDtoList;
    }
    public Page<Board> boardrecommendList(Pageable pageable) {
        return boardRepository.findByBoardRecommendPost(pageable);
    }

    /*@Transactional
    public void increaseRecommend(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        board.setRecommend(board.getRecommend() + 1);
        boardRepository.save(board);
    }

    @Transactional
    public void decreaseRecommend(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        board.setRecommend(board.getRecommend() - 1);
        boardRepository.save(board);
    } */
    @Transactional
    public void increaseRecommend(int boardId, String userEmail) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        board.setRecommend(board.getRecommend() + 1);
        boardRepository.save(board);

        Recommendation userRecommendation = new Recommendation();
        userRecommendation.setUserId(userEmail);
        userRecommendation.setBoard(board);
        userRecommendationBoardRepository.save(userRecommendation);
    }

    @Transactional
    public void decreaseRecommend(int boardId, String userEmail) {
        // 게시물 엔티티 가져오기
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));

        // 추천 수 감소
        board.setRecommend(board.getRecommend() - 1);
        boardRepository.save(board);

        // 추천 기록 가져오기 및 삭제
        Recommendation userRecommendation = userRecommendationBoardRepository
                .findByUserIdAndBoard(userEmail, board);
        if (userRecommendation != null) {
            userRecommendationBoardRepository.delete(userRecommendation);
        }
    }
    public boolean hasUserRecommended(int boardId, String userEmail) {
        // 게시물 엔티티 가져오기
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));

        // 추천 기록 존재 여부 확인
        return userRecommendationBoardRepository.existsByUserIdAndBoard(userEmail, board);
    }
    public void delete(int boardId) {
        boardRepository.deleteById(boardId);
    }
}
