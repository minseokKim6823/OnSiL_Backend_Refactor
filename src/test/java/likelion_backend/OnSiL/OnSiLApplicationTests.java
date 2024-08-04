package likelion_backend.OnSiL;

import com.fasterxml.jackson.core.JsonProcessingException;
import likelion_backend.OnSiL.domain.board.controller.BoardController;
import likelion_backend.OnSiL.domain.board.dto.BoardRequestDTO;
import likelion_backend.OnSiL.domain.board.dto.BoardResponseDTO;
import likelion_backend.OnSiL.domain.board.entity.Board;
import likelion_backend.OnSiL.domain.board.repository.BoardRepository;
import likelion_backend.OnSiL.domain.board.repository.MemberRepository;
import likelion_backend.OnSiL.domain.board.repository.UserRecommendationBoardRepository;
import likelion_backend.OnSiL.domain.board.service.BoardService;
import likelion_backend.OnSiL.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import likelion_backend.OnSiL.global.util.S3FileUploadController;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.transaction.annotation.Transactional;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;





@SpringBootTest
class OnSiLApplicationTests {

	@InjectMocks
	private BoardService boardService;

	@Mock
	private BoardRepository boardRepository;


	@Autowired
	@Mock
	private BoardController boardController;

	@Mock
	private UserRecommendationBoardRepository userRecommendationBoardRepository;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private Authentication authentication;

	@Mock
	private SecurityContext securityContext;

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
	void testSaveBoard() {
		// given
		BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
				.title("mytitle")
				.content("hello")
				.category(Board.Category.SAN)
				.build();

		Member mockMember = new Member();
		when(memberRepository.findByMemberId("test@example.com")).thenReturn(Optional.of(mockMember));

		// when
		assertDoesNotThrow(() -> boardService.save(boardRequestDTO));

		// then
		verify(boardRepository, times(1)).save(any(Board.class));
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
