package moon.codingmate.springvuejspractice.response;

import lombok.Builder;
import lombok.Getter;
import moon.codingmate.springvuejspractice.repository.BoardRepository;

@Getter
public class BoardResponse {

    private final Long id;
    private final String title;
    private final String content;

    @Builder
    private BoardResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }
}
