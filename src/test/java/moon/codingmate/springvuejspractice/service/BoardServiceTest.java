package moon.codingmate.springvuejspractice.service;

import moon.codingmate.springvuejspractice.domain.Board;
import moon.codingmate.springvuejspractice.repository.BoardRepository;
import moon.codingmate.springvuejspractice.request.BoardCreate;
import moon.codingmate.springvuejspractice.response.BoardResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장")
    void updateBoard() {
        //given
        BoardCreate boardCreate = BoardCreate.builder()
                .title("import thing")
                .content("not to give up but to stand up and reflect")
                .build();

        //when
        boardService.write(boardCreate);

        //then
        Assertions.assertEquals(1L, boardRepository.count());
        Board board = boardRepository.findAll().get(0);
        assertEquals("import thing", board.getTitle());
        assertEquals("not to give up but to stand up and reflect", board.getContent());
    }

    @Test
    @DisplayName("게시글 1개 조회 - 제목 10자리까지만 반환")
    void getBoard() {
        // given
        Board requestBoard = Board.builder()
                .title("구글1234567890")
                .content("codingmatemoon")
                .build();
        boardRepository.save(requestBoard);
        // when
        BoardResponse boardResponse = boardService.getBoard(requestBoard.getId());

        // then
        assertNotNull(boardResponse);
        assertEquals(1L, boardRepository.count());
        assertEquals("구글12345678", boardResponse.getTitle());
        assertEquals("codingmatemoon", boardResponse.getContent());
    }

    @Test
    @DisplayName("글 여러 개 조회")
    void getBoards() throws Exception {
        // given
        Board board1 = Board.builder()
                .title("하루의끝")
                .content("수고했어요")
                .build();
        boardRepository.save(board1);

        Board board2 = Board.builder()
                .title("피치피치")
                .content("휙휙")
                .build();
        boardRepository.save(board2);

        //when
        List<Board> boards = boardService.getBoards();

        assertEquals(2L, boards.size());
    }
}