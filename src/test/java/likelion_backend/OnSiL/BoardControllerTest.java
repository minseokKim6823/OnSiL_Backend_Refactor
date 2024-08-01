package likelion_backend.OnSiL;

import likelion_backend.OnSiL.domain.board.entity.Board;
import likelion_backend.OnSiL.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardRepository boardRepository;

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.setRecommend(0);
        board.setTitle("Test Board"); // Ensure required fields are set
        board.setContent("Test Content"); // Ensure required fields are set
        //boardRepository.save(board);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testIncreaseRecommend_Success() throws Exception {
        // Perform the request to increase recommendation
        ResultActions result = mockMvc.perform(post("/onsil/board/recommend/up/" + board.getPost_id()))
                .andExpect(status().isOk());

        // Verify the board recommendation count
        Board updatedBoard = boardRepository.findById(board.getPost_id()).orElseThrow();
        assertThat(updatedBoard.getRecommend()).isEqualTo(1);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testDecreaseRecommend_Success() throws Exception {
        // Set recommend to 1 and save
        board.setRecommend(1);
        boardRepository.save(board);

        // Perform the request to decrease recommendation
        ResultActions result = mockMvc.perform(post("/onsil/board/recommend/down/" + board.getPost_id()))
                .andExpect(status().isOk());

        // Verify the board recommendation count
        Board updatedBoard = boardRepository.findById(board.getPost_id()).orElseThrow();
        assertThat(updatedBoard.getRecommend()).isEqualTo(0);
    }
}
