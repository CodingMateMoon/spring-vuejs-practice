package moon.codingmate.springvuejspractice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moon.codingmate.springvuejspractice.domain.Board;
import moon.codingmate.springvuejspractice.repository.BoardRepository;
import moon.codingmate.springvuejspractice.request.BoardCreate;
import org.springframework.stereotype.Service;

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

    public Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
    }
}
