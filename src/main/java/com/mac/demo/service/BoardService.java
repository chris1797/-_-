package com.mac.demo.service;

import com.github.pagehelper.PageInfo;
import com.mac.demo.mappers.AttachMapper;
import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.mappers.UserMapper;
import com.mac.demo.model.Attach;
import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.model.User;
import com.mac.demo.service_interface.BoardServiceImpl_;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService implements BoardServiceImpl_ {

    private final BoardMapper boardDao;
    private final UserMapper userDao;
    private final AttachMapper attDao;
    ResourceLoader resourceLoader;

    @Override
    public List<Board> getBoardList(String category) {
        return boardDao.getBoardList(category);
    }

    @Override
    public List<Board> getNoticeList() {
        return null;
    }

    @Override
    public User getOne(String idMac) {
        return null;
    }

    @Override
    public boolean save(Board board) {
        return false;
    }

    @Override
    public Board getDetail(int numMac, String categoryMac) {
        return null;
    }

    @Override
    public Board getNoticeDetail(int num) {
        return null;
    }

    @Override
    public boolean delete(int num) {
        return false;
    }

    @Override
    public boolean noticeDelete(int num) {
        return false;
    }

    @Override
    public boolean update(Board board) {
        return false;
    }

    @Override
    public boolean noticeUpdate(Board board) {
        return false;
    }

    @Override
    public List<Comment> getCommentList(int num) {
        return null;
    }

    @Override
    public boolean commentSave(Comment comment) {
        return false;
    }

    @Override
    public boolean commentDelete(int numMac) {
        return false;
    }

    @Override
    public List<Board> getFreeListByKeyword(String titleMac) {
        return null;
    }

    @Override
    public List<Board> getFreeListByNickName(String nickNameMac) {
        return null;
    }

    @Override
    public List<Board> getAdsListByKeyword(String titleMac) {
        return null;
    }

    @Override
    public List<Board> getAdsListByNickName(String nickNameMac) {
        return null;
    }

    @Override
    public List<Board> getNoticeListByKeyword(String titleMac) {
        return null;
    }

    @Override
    public List<Board> getNoticeListByNickName(String nickNameMac) {
        return null;
    }

    @Override
    public List<Attach> getFileSet(Board board, MultipartFile[] mfiles, HttpServletRequest request) {
        return null;
    }

    @Override
    public List<Attach> getUpdateFileSet(Board board, MultipartFile[] mfiles, HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean fileinsert(Board board, MultipartFile[] mfiles, HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean fileupdate(Board board, MultipartFile[] mfiles, HttpServletRequest request) {
        return false;
    }

    @Override
    public List<Attach> getFileList(int pcodeMac) {
        return null;
    }

    @Override
    public String getFname(int num) {
        return null;
    }

    @Override
    public boolean filedelete(int num) {
        return false;
    }

    @Override
    public ResponseEntity<Resource> download(HttpServletRequest request, int FileNum) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public PageInfo<Board> getPageInfo(String categoryMac) {
        return null;
    }
}
