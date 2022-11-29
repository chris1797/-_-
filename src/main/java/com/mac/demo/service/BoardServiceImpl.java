package com.mac.demo.service;

import com.github.pagehelper.PageInfo;
import com.mac.demo.mappers.AttachMapper;
import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.mappers.UserMapper;
import com.mac.demo.model.Attach;
import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.model.User;
import com.mac.demo.serviceImpl.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardDao;
    private final UserMapper userDao;
    private final AttachMapper attachDao;
    ResourceLoader resourceLoader;

    @Override
    public List<Board> getBoardList(String category) {
        return boardDao.getBoardList(category);
    }

    @Override
    public PageInfo<Board> getPageInfo(String categoryMac) {
        PageInfo<Board> pageInfo = new PageInfo<>(getBoardList(categoryMac));
        return pageInfo;
    }

    @Override
    public User getOne(String idMac) {
        return userDao.getOne(idMac);
    }

    @Override
    public boolean save(Board board) {
        return 0 < boardDao.save(board);
    }

    @Override
    public Board getDetail(int numMac, String categoryMac) {
        return boardDao.getDetail(numMac, categoryMac);
    }

    @Override
    public boolean delete(int num) {
        return 0 > boardDao.delete(num);
    }

    @Override
    public boolean update(Board board) {
        return 0 < boardDao.update(board);
    }

    @Override
    public List<Comment> getCommentList(int num) {
        return boardDao.getCommentList(num);
    }

    @Override
    public boolean commentSave(Comment comment) {
        return 0 < boardDao.commentsave(comment);
    }

    @Override
    public boolean commentDelete(int numMac) {
        return 0 < boardDao.commentdelete(numMac);
    }

    @Override
    public List<Board> getListByKeyword(String title, String category) {
        return boardDao.getListByKeyword(title, category);
    }

    @Override
    public List<Board> getListByNickName(String nickName, String category) {
        return boardDao.getListByNickName(nickName, category);
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
        return attachDao.getFileList(pcodeMac);
    }

    @Override
    public String getFname(int num) {
        return attachDao.getFname(num);
    }

    @Override
    public boolean filedelete(int num) {
        int removed = attachDao.filedelete(num);
        return removed > 0;
    }

    @Override
    public ResponseEntity<Resource> download(String contentType, Resource resource) throws UnsupportedEncodingException {
        if (contentType == null) contentType = "application/octet-stream";

        ResponseEntity<Resource> file =  ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(resource.getFilename().getBytes("UTF-8"), "ISO-8859-1") + "\"")

                // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                // HttpHeaders.CONTENT_DISPOSITION는 http header를 조작하는 것, 화면에 띄우지 않고 첨부화면으로
                // 넘어가게끔한다
                // filename=\"" + resource.getFilename() + "\"" 는 http프로토콜의 문자열을 고대로 쓴 것
                .body(resource);

        return file;
    }

}
