package moon.codingmate.springvuejspractice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import moon.codingmate.springvuejspractice.domain.Board;
import moon.codingmate.springvuejspractice.domain.QBoard;
import moon.codingmate.springvuejspractice.request.BoardSearch;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> getList(int page) {
        return jpaQueryFactory.selectFrom(QBoard.board)
                .limit(10)
                .offset((long) (page - 1) * 10) // 특정 page에 속하는 데이터 10개 표기
                .fetch();
    }

    @Override
    public List<Board> getList(BoardSearch boardSearch) {
        return jpaQueryFactory.selectFrom(QBoard.board)
                .limit(boardSearch.getSize())
                .offset(boardSearch.getOffset())
                .orderBy(QBoard.board.id.desc())
                .fetch();
    }
}
