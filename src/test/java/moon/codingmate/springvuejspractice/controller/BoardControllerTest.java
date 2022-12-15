package moon.codingmate.springvuejspractice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import moon.codingmate.springvuejspractice.domain.Board;
import moon.codingmate.springvuejspractice.repository.BoardRepository;
import moon.codingmate.springvuejspractice.request.BoardCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class BoardControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardRepository boardRepository;

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
        BoardCreate boardCreate = BoardCreate.builder()
                .title("성장")
                .content("여정을 즐기고 시스템을 지키며 강화하기")
                .build();

        String json = objectMapper.writeValueAsString(boardCreate);
        System.out.println(json);

        mockMvc.perform(MockMvcRequestBuilders.post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("content null 게시글 조회 요청")
    void getBoard_nullContent() throws Exception {

        BoardCreate boardCreate = BoardCreate.builder()
                .title("성장")
                .build();

        String json = objectMapper.writeValueAsString(boardCreate);
        System.out.println(json);

        mockMvc.perform(MockMvcRequestBuilders.post("/board_bindingResult")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("내용을 입력해주세요"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("/post 요청 시 title값은 필수입니다.")
    void postTest() throws Exception {

        BoardCreate boardCreate = BoardCreate.builder()
                .content("여정을 즐기고 시스템을 지키며 강화하자")
                .build();

        String json = objectMapper.writeValueAsString(boardCreate);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/board/update 요청 시 DB에 값을 저장합니다.")
    void updateBoardTest() throws Exception {

        BoardCreate boardCreate = BoardCreate.builder()
                .title("성장")
                .content("enjoy the journey, protect and strengthen the system")
                .build();

        String json = objectMapper.writeValueAsString(boardCreate);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/board/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        assertEquals(1L, boardRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회- 제목 10자리까지만 반환")
    void getBoardById() throws Exception {
        // given
        Board board = Board.builder()
                .title("하루1234567890")
                .content("하나씩")
                .build();
        boardRepository.save(board);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/board/{boardId}", board.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(board.getId()))
                .andExpect(jsonPath("$.title").value("하루12345678"))
                .andExpect(jsonPath("$.content").value("하나씩"))
                .andDo(MockMvcResultHandlers.print());
    }

}