package moon.codingmate.springvuejspractice.repository;

import moon.codingmate.springvuejspractice.domain.Board;

import java.util.List;

public interface BoardRepositoryCustom {
    List<Board> getList(int page);
}
