package moon.codingmate.springvuejspractice.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.min;

@Getter
@Setter
@Data
@Builder
public class BoardSearch {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset() {
        return (long) (Math.max(1, page)) * min(size, MAX_SIZE);
    }
}
