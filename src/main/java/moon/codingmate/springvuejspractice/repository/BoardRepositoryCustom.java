package moon.codingmate.springvuejspractice.repository;

import moon.codingmate.springvuejspractice.domain.Board;
import moon.codingmate.springvuejspractice.request.BoardSearch;

import java.util.List;

public interface BoardRepositoryCustom {
    List<Board> getList(int page);
    List<Board> getList(BoardSearch boardSearch);
}
