package moon.codingmate.springvuejspractice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moon.codingmate.springvuejspractice.domain.Board;
import moon.codingmate.springvuejspractice.repository.BoardRepository;
import moon.codingmate.springvuejspractice.request.BoardCreate;
import moon.codingmate.springvuejspractice.response.BoardResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void write(BoardCreate boardCreate) {

        Board board = Board.builder()
                .title(boardCreate.getTitle())
                .content(boardCreate.getContent())
                .build();
        boardRepository.save(board);
    }

    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        BoardResponse boardResponse = BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();

        return boardResponse;
    }

    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
                .map(BoardResponse::new
                )
                .collect(Collectors.toList());
    }

    public List<BoardResponse> getBoards(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return boardRepository.findAll(pageable).stream()
                .map(board -> new BoardResponse(board))
                .collect(Collectors.toList());
    }

    public List<BoardResponse> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable).stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    public List<BoardResponse> getBoardsWithJPAQueryFactory(Pageable pageable) {
        return boardRepository.getList(1).stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }
}
