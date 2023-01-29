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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        List<BoardResponse> boards = boardService.getBoards();

        assertEquals(2L, boards.size());
    }

    @Test
    @DisplayName("게시글 여러 개 저장")
    void saveBoards() {
        // given
        boardRepository.saveAll(List.of(Board.builder()
                        .title("구글")
                        .content("codingmatemoon")
                        .build(),
                Board.builder()
                        .title("IBM")
                        .content("UCLA")
                        .build()));

        // when
        List<BoardResponse> boards = boardService.getBoards();
        // then
        assertEquals(2L, boards.size());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void getBoardPage() {
        // given
        List<Board> requestBoards = IntStream.range(1, 31)
                .mapToObj(i -> Board.builder()
                        .title("google " + i)
                        .content("codingmatemoon " + i)
                        .build())
                .collect(Collectors.toList());
        boardRepository.saveAll(requestBoards);
        // when
        List<BoardResponse> boards = boardService.getBoards(0);

        // then
        assertEquals(5L, boards.size());
        assertEquals("google 1",boards.get(0).getTitle());
    }

    @Test
    @DisplayName("Pageable 활용 글 1페이지 조회")
    void getBoardWithJPAQueryFactory() {
        // given
        List<Board> requestBoards = IntStream.range(1, 31)
                .mapToObj(i -> Board.builder()
                        .title("google " + i)
                        .content("codingmatemoon " + i)
                        .build())
                .collect(Collectors.toList());
        boardRepository.saveAll(requestBoards);
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        // when
        List<BoardResponse> boards = boardService.getBoardsWithJPAQueryFactory(pageable);
        // then
        assertEquals(10L, boards.size());
        assertEquals("google 1",boards.get(0).getTitle());
    }

}