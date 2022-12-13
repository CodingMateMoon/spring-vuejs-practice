package moon.codingmate.springvuejspractice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moon.codingmate.springvuejspractice.domain.Board;
import moon.codingmate.springvuejspractice.request.BoardCreate;
import moon.codingmate.springvuejspractice.service.BoardService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boardGet")
    public String getBoardGet(@RequestParam String title, @RequestParam String content) {
        log.info("title={}, content={}", title, content);
        return "getBoard";
    }

    @PostMapping("/board_bindingResult")
    public Map<String, String> getBoard_BindingResult(@RequestBody @Valid BoardCreate boardCreate, BindingResult result) {
        log.info("boardRequest={}", boardCreate);
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField(); // title
            String errorMessage = firstFieldError.getDefaultMessage(); // 에러 메시지

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }
        return Map.of();
    }

    @PostMapping("/post")
    public Map<String, String> postTest(@RequestBody @Valid BoardCreate boardCreate) {
        log.info("boardRequest={}", boardCreate);
        return Map.of();
    }

    @PostMapping("/board/update")
    public Map<String, String> updateBoard(@RequestBody @Valid BoardCreate request) {
        boardService.write(request);
        return Map.of();
    }

    @GetMapping("/board/{boardId}")
    public Board getBoard(@PathVariable(name = "boardId") Long id) {
        return boardService.getBoard(id);
    }

}
