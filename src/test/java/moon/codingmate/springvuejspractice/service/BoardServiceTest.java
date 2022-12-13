package moon.codingmate.springvuejspractice.service;

import moon.codingmate.springvuejspractice.domain.Board;
import moon.codingmate.springvuejspractice.repository.BoardRepository;
import moon.codingmate.springvuejspractice.request.BoardCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("게시글 1개 조회")
    void getBoard() {
        // given
        Board requestBoard = Board.builder()
                .title("구글")
                .content("codingmatemoon")
                .build();
        boardRepository.save(requestBoard);
        // when
        Board board = boardService.getBoard(requestBoard.getId());

        // then
        assertNotNull(board);
        assertEquals(1L, boardRepository.count());
        assertEquals("구글", board.getTitle());
        assertEquals("codingmatemoon", board.getContent());
    }
}