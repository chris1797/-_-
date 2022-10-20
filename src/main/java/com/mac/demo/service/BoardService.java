package com.mac.demo.service;

import com.mac.demo.model.Board;
import com.mac.demo.model.User;

import java.util.List;

public interface BoardService {

    // 게시판 목록
    public List<Board> getBoardList(String categorymac);
    public List<Board> getNoticeList();

    // 유저 ID를 통한 회원정보
    public User getOne(String idMac);

    // 게시글 저장
    public boolean save(Board board);


}
