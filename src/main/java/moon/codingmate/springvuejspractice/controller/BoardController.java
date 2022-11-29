package moon.codingmate.springvuejspractice.controller;

import lombok.extern.slf4j.Slf4j;
import moon.codingmate.springvuejspractice.request.BoardRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class BoardController {

    @GetMapping("/boardGet")
    public String getBoardGet(@RequestParam String title, @RequestParam String content) {
        log.info("title={}, content={}", title, content);
        return "getBoard";
    }

    @PostMapping("/board")
    public String getBoard(@RequestBody @Valid BoardRequest boardRequest) {
        log.info("boardRequest={}", boardRequest);
        return "getBoard";
    }
}
