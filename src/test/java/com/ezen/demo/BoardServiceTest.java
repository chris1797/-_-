package com.ezen.demo;

import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.model.Board;
import com.mac.demo.service.BoardService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BoardService.class)
public class BoardServiceTest {

    @Autowired
    BoardMapper boardDao;

    BoardService svc;

    @BeforeEach
    void init() {
        svc = new BoardService(boardDao);
    }

    @DisplayName("로그인 성공")
    @Test
    public void save() {

        String title = "title_test";
        String contents = "contents_test";

        Board board = new Board();
        board.setTitleMac(title);
        board.setContentsMac(contents);

        svc.save(board);
    }
}
