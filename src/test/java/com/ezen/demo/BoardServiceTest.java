package com.ezen.demo;

import com.mac.demo.DemoApplication;
import com.mac.demo.mappers.AttachMapper;
import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.mappers.UserMapper;
import com.mac.demo.model.Board;
import com.mac.demo.service.BoardService;
import com.mac.demo.serviceImpl.BoardServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
@MybatisTest
//@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardServiceTest {

    @Autowired
    BoardService svc;

    @Autowired
    BoardMapper boardDao;

    @Autowired
    UserMapper userDao;

    @Autowired
    AttachMapper attachDao;

//    @BeforeEach
//    void init() {
//        this.svc = new BoardService();
//    }

    @Test
    public void save_Test() throws Exception{

        // given
        String title = "title_test";
        String contents = "contents_test";

        Board board = new Board();

        board.setTitleMac(title);
        board.setContentsMac(contents);
        board.setNickNameMac("test");
        board.setCategoryMac("free");

        boolean saved = svc.save(board);

        // then
        assertThat(saved).isEqualTo(true);
    }
}
