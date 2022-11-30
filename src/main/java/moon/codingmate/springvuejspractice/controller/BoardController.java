package moon.codingmate.springvuejspractice.controller;

import lombok.extern.slf4j.Slf4j;
import moon.codingmate.springvuejspractice.request.BoardRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class BoardController {

    @GetMapping("/boardGet")
    public String getBoardGet(@RequestParam String title, @RequestParam String content) {
        log.info("title={}, content={}", title, content);
        return "getBoard";
    }

    @PostMapping("/board")
    public Map<String, String> getBoard(@RequestBody @Valid BoardRequest boardRequest, BindingResult result) {
        log.info("boardRequest={}", boardRequest);
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
}
