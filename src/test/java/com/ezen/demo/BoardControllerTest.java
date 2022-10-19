package com.ezen.demo;

import com.mac.demo.service.BoardService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BoardService boardService;

    @AfterEach
    public void cleanup(int nummac) {
        boardService.delete(nummac);
    }

    @Test
    public void save() {
        String title = "title_test";
        String contents = "contents_test";


    }
}
