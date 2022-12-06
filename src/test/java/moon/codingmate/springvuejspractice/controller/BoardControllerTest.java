package moon.codingmate.springvuejspractice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
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
    void getBoardGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/boardGet")
                        .param("title", "업데이트 공지")
                        .param("content", "내용")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("getBoard"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 조회합니다.")
    void getBoard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/board")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("title", "업데이트 공지")
//                        .param("content", "내용")
                       .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("getBoard"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("content null 게시글 조회 요청")
    void getBoard_nullContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/board_bindingResult")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("title", "업데이트 공지")
//                        .param("content", "내용")
                        .content("{\"title\": \"제목입니다.\", \"content\": \"\"}"))
                .andExpect(status().isOk())
//                .andExpect(content().string("getBoard"))
                .andExpect(jsonPath("$.content").value("내용을 입력해주세요"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("/posts 요청 시 title값은 필수입니다.")
    void postTest() throws Exception {  // 가능하면 application/json을 권장합니다. (기존 application/x-www-form-urlencoded)

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다.\"}"))
                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.title").value("제목을 입력해주세요"))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

}