package moon.codingmate.springvuejspractice.request;

import lombok.*;


import javax.validation.constraints.NotBlank;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardCreate {


    @NotBlank(message = "제목을 입력해주세요")
    String title;

    @NotBlank(message = "내용을 입력해주세요")
    String content;

    @Builder
    public BoardCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
