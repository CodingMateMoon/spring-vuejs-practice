package moon.codingmate.springvuejspractice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class BoardRequest {

    @NotBlank
    String title;
    String content;
}
