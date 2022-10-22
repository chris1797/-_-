package com.mac.demo;

import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.model.Board;
import com.mac.demo.service.BoardService;
import com.mac.demo.serviceImpl.BoardServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardMapperTest {

    @Autowired
    private BoardMapper boardDao;

    @DisplayName("boardMapper Test")
    @Test
    public void board_Test() throws Exception{

        Board board = boardDao.getDetail(406,"free");

        // then
        Assert.assertEquals(board.getNumMac(), 406);
    }
}
