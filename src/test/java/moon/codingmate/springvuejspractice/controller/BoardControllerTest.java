package moon.codingmate.springvuejspractice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("게시글을 조회합니다.")
    void getBoard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/board")
                        .param("title", "업데이트 공지")
                        .param("")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("getBoard"))
                .andDo(MockMvcResultHandlers.print());
    }
}