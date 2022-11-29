package com.mac.demo.serviceImpl;

import com.github.pagehelper.PageInfo;
import com.mac.demo.model.Attach;
import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.model.User;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface BoardService {

    // 게시판 목록
    public List<Board> getBoardList(String categorymac);
//  public List<Board> getNoticeList();

    // 유저 ID를 통한 회원정보
    public User getOne(String idMac);

    // 게시글 저장
    public boolean save(Board board);

    // 게시글 상세보기
    public Board getDetail(int numMac, String categoryMac);
//    public Board getNoticeDetail(int num);

    // 게시글 삭제
    public boolean delete(int num);
//    public boolean noticeDelete(int num);

    // 게시글 수정
    public boolean update(Board board);
//    public boolean noticeUpdate(Board board);

    // 댓글 리스트
    public List<Comment> getCommentList(int num);

    // 댓글 작성
    public boolean commentSave(Comment comment);

    // 댓글 삭제
    public boolean commentDelete(int numMac);

    // 게시글 검색
    public List<Board> getListByKeyword(String title, String category);
    public List<Board> getListByNickName(String nickName, String category);
//    public List<Board> getAdsListByKeyword(String titleMac);
//    public List<Board> getAdsListByNickName(String nickNameMac);
//    public List<Board> getNoticeListByKeyword(String titleMac);
//    public List<Board> getNoticeListByNickName(String nickNameMac);

    // 파일데이터 set
    public List<Attach> getFileSet(Board board, MultipartFile[] mfiles, HttpServletRequest request);

    // 게시글 내 파일 수정을 위한 파일데이터 set
    public List<Attach> getUpdateFileSet(Board board, MultipartFile[] mfiles, HttpServletRequest request);

    // 파일데이터 정보 DB에 저장
    public boolean fileinsert(Board board, MultipartFile[] mfiles, HttpServletRequest request);

    // 파일데이터 정보 DB 수정
    public boolean fileupdate(Board board, MultipartFile[] mfiles, HttpServletRequest request);

    // 글넘버를 받아 해당 글의 파일데이터 리스트 가져오기
    public List<Attach> getFileList(int pcodeMac);

    // 파일 No.를 통해 해당 파일의 이름 불러오기
    public String getFname(int num);

    // 파일 No.를 통해 해당 파일데이터 DB에서 삭제
    public boolean filedelete(int num);

    // 파일 다운로드
    public ResponseEntity<Resource> download (String contentType, Resource resource) throws UnsupportedEncodingException;

    // 게시판 카테고리에 따른 페이지네이션
    public PageInfo<Board> getPageInfo(String categoryMac);
}
