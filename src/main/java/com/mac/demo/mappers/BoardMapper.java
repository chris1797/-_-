package com.mac.demo.mappers;

import java.awt.print.Pageable;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.model.User;


@Mapper
public interface BoardMapper {

//	게시글 저장
	int save(Board board);

//	게시글 수정
	int update(Board board);
	int Noticeedit(Board board);
	
	int delete(int num); //게시글 삭제
	int Noticedelete(int num); 
	
	int commentAllDelete(int num);//게시글 삭제하면 댓글도 삭제
	
	Board getDetail(int numMac, String categoryMac); //게시글 상세보기
	Board getNoticeDetail(int num);
	
//	댓글
	int commentsave(Comment comment);
	List<Comment> getCommentList(int num); //자유게시판
	int commentdelete(int numMac);

//	유저
	User getUserById(String idMac);

	
//	게시글 목록
	List<Board> getBoardList(String categoryMac); // 자유게시판
	List<Board> getNoticeList(); // 공지게시판
	
//	닉네임, 글내용+제목 검색
	List<Board> getListByKeyword(String keyword, String category);
	List<Board> getListByNickName(String nickName, String category);
	
//	List<Board> getAdsListByKeyword(String keyword);
//	List<Board> getAdsListByNickName(String nickNameMac);
	
//	List<Board> getNoticeListByKeyword(String keyword);
//	List<Board> getNoticeListByNickName(String nickNameMac);
	

	
//	myPage
	List<Board> getMypageInFreeBoard(String idMac);
	List<Board> getMypageInAdsBoard(String idMac);
	
	Page<Board> getList(Pageable pageable);
	

}