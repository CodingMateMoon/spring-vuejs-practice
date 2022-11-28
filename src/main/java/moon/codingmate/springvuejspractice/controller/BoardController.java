package moon.codingmate.springvuejspractice.controller;

import lombok.extern.slf4j.Slf4j;
import moon.codingmate.springvuejspractice.request.BoardRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BoardController {

    @GetMapping("/boardGet")
    public String getBoardGet(@RequestParam String title, @RequestParam String content) {
        log.info("title={}, content={}", title, content);
        return "getBoard";
    }

    @PostMapping("/board")
    public String getBoard(BoardRequest boardRequest) {
        log.info("boardRequest={}", boardRequest);
        return "getBoard";
    }
}
