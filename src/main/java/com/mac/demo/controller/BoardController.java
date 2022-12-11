package com.mac.demo.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mac.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.serviceImpl.BoardService;


@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService svc;
	private final UserService userSvc;

	ResourceLoader resourceLoader;


	/** 게시판 메인화면 */
	@GetMapping("/main")
	public String main() {
		return "thymeleaf/mac/board/board_main";
	}
	
//======================================== 게시판 =======================================
	/** 신규 게시글 작성페이지로 이동 */
	@GetMapping("/{categoryMac}/writingPage")
	public String insertPage(Model model,
							  HttpSession session,
							  @PathVariable("categoryMac") String categoryMac) {
		
		System.out.println("현재 접속한 ID : " + (String)session.getAttribute("idMac"));
		
		/**
		 * 글쓰기 기능은 회원만 사용할 수 있도록 구현
		 * 글쓰기 페이지로 이동하기 전 로그인 여부 체크
		 */
		if ((String)session.getAttribute("idMac") == null) {
			model.addAttribute("msg", "로그인 후 사용 가능합니다.");
			model.addAttribute("board", new Board());
		} else {
			String id = (String)session.getAttribute("idMac");

			// Board 객체에 로그인 한 유저의 닉네임, 게시판 카테고리 전달
			Board board = new Board();
			board.setNickNameMac(svc.getOne(id).getNickNameMac());
			board.setCategoryMac(categoryMac);
			model.addAttribute("board", board);
			
			// 현재 세션에 로그인된 ID 전달, insertform에서는 hidden으로 담아서 save
			model.addAttribute("idMac", id);
		}
		return String.format("thymeleaf/mac/board/%s_board_input", categoryMac);
	}
	

	/** 신규 게시글 저장 */
	@PostMapping("/{categoryMac}/new")
	@ResponseBody
	public String save(Board board,
					   @PathVariable("categoryMac") String categoryMac,
					   @RequestParam("files") MultipartFile[] mfiles,
					   HttpServletRequest request) {
		svc.save(board);
		// mfile 객체 배열의 첫번째 값이 비었는지 검사, 파일이 첨부되었을 경우에만 실행
		if(mfiles[0].isEmpty() != true) {
			svc.fileinsert(board, mfiles, request);
		}
		// 게시글 저장 후 해당 글의 num을 다시 json 형태로 전달하여 글쓰기 완료 후 해당 글의 상세페이지로 이동되도록 구현
		return String.format("{\"savednum\":\"%d\"}", board.getNumMac());
	}
	
	/** 게시글 목록페이지로 이동 */
	@GetMapping("/{categoryMac}/list")
	public String getListByPage(@RequestParam(name="page", required = false,defaultValue = "1") int page,
								@PathVariable("categoryMac") String categoryMac,
								Model model,
								HttpSession session) {
		PageHelper.startPage(page, 10);
		model.addAttribute("page", page);
		model.addAttribute("pageInfo", svc.getPageInfo(categoryMac));
		model.addAttribute("idMac",(String)session.getAttribute("idMac"));
		
		return String.format("thymeleaf/mac/board/%s_board_list", categoryMac);
	}
	
	
	/**  게시글 보기 */
	@GetMapping("/{categoryMac}/{num}")
	public String getDetail(@PathVariable("num") int num,
							@PathVariable("categoryMac") String categoryMac,
							@RequestParam(name="page", required = false,defaultValue = "1") int page, 
							Model model,
							HttpSession session) {

		/**
		 * 댓글 작성은 로그인 한 유저만 가능하도록 구현
		 * Comment 객체 생성 후 현재 세션에 접속한 유저정보 담아 전달
		 */
		Comment comment = new Comment();
		if(session.getAttribute("idMac") != null) {
			comment.setIdMac((String) session.getAttribute("idMac"));
			comment.setNickNameMac(svc.getOne((String)session.getAttribute("idMac")).getNickNameMac());	
			comment.setPcodeMac(num);
			model.addAttribute("comment", comment);
			model.addAttribute("idMac", (String)session.getAttribute("idMac"));
		} else {
			model.addAttribute("msg", "로그인 후 작성 가능합니다.");
		}
		
		/** 게시판에서 글넘버, 게시판 카테고리를 전달받아 해당 글 조회 */
		model.addAttribute("board", svc.getDetail(num, categoryMac));
		
		/** 댓글리스트 페이지네이션 */
		PageHelper.startPage(page, 7);
		model.addAttribute("page", page);
		model.addAttribute("pageInfo", new PageInfo<>(svc.getCommentList(num)));

		/** 해당 게시글에 첨부된 파일리스트 조회 */
		model.addAttribute("filelist", svc.getFileList(num));
		model.addAttribute("fileindex", svc.getFileList(num).size());

		return String.format("thymeleaf/mac/board/%s_board_detail", categoryMac);
	}

	/**
	 * 게시글 삭제
	 * PostMapping 방식으로 form 밖에 있는 데이터를 넘기지 못해 get으로 우선 구현
	 */
	@DeleteMapping("/{categoryMac}/{num}")
	@ResponseBody
	public String delete(@PathVariable("num") int num,
									  @PathVariable("categoryMac") String categoryMac) {
		return String.format("{\"deleted\":\"%b\"}", svc.delete(num));
	}
	
  	/** 게시글 수정페이지로 이동 */
	@GetMapping("/{categoryMac}/{num}/editPage")
	public String update(@PathVariable("num") int num, 
						 HttpSession session,
						 Model model,
						 @PathVariable("categoryMac") String categoryMac) {
		model.addAttribute("idMac", (String)session.getAttribute("idMac"));
		model.addAttribute("board", svc.getDetail(num, categoryMac));
		model.addAttribute("filelist", svc.getFileList(num));
		model.addAttribute("fileindex", svc.getFileList(num).size());
		
		return String.format("thymeleaf/mac/board/%s_board_edit", categoryMac);
	}
	
  	/** 게시글 수정 */
	@PutMapping("/{categoryMac}")
	@ResponseBody
	public String modify(Board newBoard,
						 @RequestParam("files") MultipartFile[] mfiles,
						 @PathVariable("categoryMac") String categoryMac,
						 HttpServletRequest request) {
		if(mfiles[0].isEmpty() != true) { svc.fileupdate(newBoard, mfiles, request); }
		return String.format("{\"updated\":\"%b\"}", svc.update(newBoard));
	}
	
	
	/** 게시글 타이틀 검색 */
	@GetMapping("/{categoryMac}/search")
	public String getListByTitle(@RequestParam(name="page", required = false,defaultValue = "1") int page,
								 @RequestParam(name="category", required = false) String category,
								 @RequestParam(name="keyword", required = false) String keyword,
								 @PathVariable("categoryMac") String categoryMac,
								 Model model) {
		
		PageHelper.startPage(page, 10);
		
		PageInfo<Board> pageInfo = null;
		if(category.equals("contents")) {
			pageInfo = new PageInfo<>(svc.getListByKeyword(keyword, categoryMac));
		} else {
			pageInfo = new PageInfo<>(svc.getListByNickName(keyword, categoryMac));
		}

		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("page", page);
		
		return String.format("thymeleaf/mac/board/%s_board_list", categoryMac);
	}
	
//======================================== 댓글 ========================================
	@PostMapping("/comment")
	@ResponseBody
	public Map<String, Object> comment(Comment comment,
									   Model model,
									   HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if((String)session.getAttribute("idMac") == null){ //세션을 가져옴
			map.put("msg", "로그인 후 사용 가능합니다.");
		} else {
			map.put("commented", svc.commentSave(comment));
		}
		
		return map;
	}
	
	
	@DeleteMapping("/comment/{numMac}")
	@ResponseBody
	public Map<String, Object> comment_delte(@PathVariable int numMac,
											 Model model,
											 HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deleted", svc.commentDelete(numMac));
		return map;
	}
//======================================== 파일 ========================================
	
	@DeleteMapping("/file/{numMac}")
	@ResponseBody
	public Map<String, Object> filDeletion(@PathVariable("numMac") int numMac,
										   Model model,
										   HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("filedeleted", svc.filedelete(numMac));
		return map;
	}
	
	@GetMapping("/file/{filenum}")
	@ResponseBody
	public ResponseEntity<Resource> fileDownload(HttpServletRequest request,
												 @PathVariable(name="filenum", required = false) int file_Num) throws Exception {

		/**
		 * 파일명 디코딩 작업
		 */
		String fileName = svc.getFname(file_Num);
		String originFilename = URLDecoder.decode(fileName, "UTF-8");

		Resource resource = resourceLoader.getResource("WEB-INF/files/" + originFilename);
		String contextType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		return svc.download(contextType, resource);
	}
}