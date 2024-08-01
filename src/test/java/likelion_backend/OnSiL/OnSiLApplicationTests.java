package likelion_backend.OnSiL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import likelion_backend.OnSiL.domain.board.controller.BoardController;
import likelion_backend.OnSiL.domain.board.dto.BoardRequestDTO;
import likelion_backend.OnSiL.domain.board.dto.BoardResponseDTO;
import likelion_backend.OnSiL.domain.board.entity.Board;
import likelion_backend.OnSiL.domain.board.repository.BoardRepository;
import likelion_backend.OnSiL.domain.board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Commit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
class OnSiLApplicationTests {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardController boardController;

	@BeforeEach
	void setUp() {
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn("test@example.com");
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	void contextLoads() {
	}

	@Test
	@Commit
	void testSaveBoard() {
		// given
		BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
				.title("5 Title")
				.content("6 Content")
				.category(Board.Category.SAN)
				.image("test-image.jpg")
				.build();

		// when & then
		assertDoesNotThrow(() -> {
			boardService.save(boardRequestDTO);
		});



	}
	@Test
	@Commit
	void testUpdateBoard() throws Exception {

		BoardRequestDTO initialBoardRequestDTO = BoardRequestDTO.builder()
				.title("3 Title")
				.content("3 Content")
				.category(Board.Category.SAN)
				.image("3-image.jpg")
				.build();

		boardService.save(initialBoardRequestDTO);

		Board savedBoard = boardRepository.findAll().get(0);

		BoardRequestDTO updatedBoardRequestDTO = BoardRequestDTO.builder()
				.title("3Title")
				.content("3 Content")
				.category(Board.Category.JIL)
				.image("3-image.jpg")
				.build();

		ObjectMapper objectMapper = new ObjectMapper();
		String updatedBoardJson = objectMapper.writeValueAsString(updatedBoardRequestDTO);


		assertDoesNotThrow(() -> {
			boardService.saveUpdate(updatedBoardJson, savedBoard.getPost_id());
		});

		Board updatedBoard = boardRepository.findById(savedBoard.getPost_id())
				.orElseThrow(() -> new EntityNotFoundException("수정된 게시물을 찾을 수 없습니다."));

	}
	@Test
	@Commit
	@Transactional
	void testDeleteBoard() {

		BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
				.title("Title to be deleted")
				.content("Content to be deleted")
				.category(Board.Category.SAN)
				.image("delete-image.jpg")
				.build();

		boardService.save(boardRequestDTO);

		List<Board> allBoards = boardRepository.findAll();
		assert !allBoards.isEmpty();

		Board savedBoard = allBoards.get(0);

		assertDoesNotThrow(() -> {
			boardService.delete(savedBoard.getPost_id());
		});

		boolean exists = boardRepository.existsById(savedBoard.getPost_id());
		assert !exists : "게시물이 삭제되지 않았습니다.";
	}

	@Test
	@Transactional
	void testBoardrecommendList() {
		// Define pageable object with size 2
		Pageable pageable = PageRequest.of(0, 2);

		// Fetch popular board list
		Page<Board> popularBoardsPage = boardService.boardrecommendList(pageable);

		// Print the results
		System.out.println("페이지 크기: " + popularBoardsPage.getSize());
		System.out.println("총 갯수: " + popularBoardsPage.getTotalElements());
		popularBoardsPage.getContent().forEach(board ->
				System.out.println("보드ID: " + board.getPost_id() + ", 제목: " + board.getTitle() + ", 추천수: " + board.getRecommend())
		);

	}
	@Test
	@Transactional
	void testSearchAllBoards() throws JsonProcessingException {
		// Test case when title is not provided (should return all boards)
		ResponseEntity<List<BoardResponseDTO>> response = boardController.search(null);

		List<BoardResponseDTO> boardList = response.getBody();

		// Print the results
		System.out.println("All Boards:");
		assert boardList != null;
		boardList.forEach(board ->
				System.out.println("Board ID: " + board.getPostId() + ", Title: " + board.getTitle() + ", Content: " + board.getContent())
		);

	}
	@Test
	@Transactional
	void testSearchBoardByTitle() throws JsonProcessingException {
		// Test case when a specific title is provided
		String title = "Test Title";
		ResponseEntity<List<BoardResponseDTO>> response = boardController.search(title);

		List<BoardResponseDTO> boardList = response.getBody();

		// Print the results
		System.out.println("Boards with Title: " + title);
		assert boardList != null;
		boardList.forEach(board ->
				System.out.println("Board ID: " + board.getPostId() + ", Title: " + board.getTitle() + ", Content: " + board.getContent())
		);

	}

//	@Test
//	@Commit
//	void testIncreaseRecommend() {
//		int boardId = 11;
//			boardService.increaseRecommend(boardId);
//
//
//	}
//
//	@Test
//	@Commit
//	void testDecreaseRecommend() {
//		int boardId = 11;
//			boardService.decreaseRecommend(boardId);
//
//
//	}
}
