package com.mac.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mac.demo.model.Board;
import com.mac.demo.model.User;
import com.mac.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
//	유저 맵퍼
	@Autowired
	private UserService svc;

//	회원가입 페이지로 이동
	@GetMapping("/addForm")
	public String addForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "thymeleaf/mac/User/addForm";
	}
	
//	아이디 중복체크 후 회원가입 다음 단계로 이동
	@PostMapping("/addForm/{idMac}")
	public String addForm2(@PathVariable("idMac")String idMac, Model model) {
		User user = new User();
		user.setIdMac(idMac);
		model.addAttribute("user", user);
		return "thymeleaf/mac/User/addForm";
	}
	
//	아이디, email 인증 후 회원가입 다음 단계로 이동
	@PostMapping("/addForm/{idMac}/{emailMac}")
	public String addForm3(@PathVariable("idMac")String idMac, 
			@PathVariable("emailMac")String emailMac, 
			@RequestParam(name ="check", defaultValue="0")String check, Model model) {
		User user = new User();
		user.setIdMac(idMac);
		user.setEmailMac(emailMac);
		if(Integer.parseInt(check) == 1) {
			model.addAttribute("user", user);
			return "thymeleaf/mac/User/addForm";
		}
		return "thymeleaf/mac/home/home";
	}
	
//	계정추가
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> add(User user) {
		Map<String, Object> map = new HashMap<>();
		boolean result = svc.add(user);
		map.put("result", result);
		return map;
	}
	
//	계정리스트
	@GetMapping("/list")
	public String list(Model model) {
		List<User> list = svc.getList();
		model.addAttribute("list", list);	
		return "thymeleaf/mac/User/userlist";
	}
	
//	마이페이지로 이동
	@GetMapping("/detail/{idMac}")
	public String mypage(@PathVariable("idMac")String idMac, Model model, HttpSession session,@RequestParam(name="page", required = false,defaultValue = "1") int page) {
		User user = svc.getOne(idMac);
	
		
		if(idMac.equals((String)session.getAttribute("idMac"))==false) {
			return "redirect:/home";
		};
	
		
		if((String)session.getAttribute("idMac") == null){ //세션을 가져옴
			return "thymeleaf/mac/home/home";
		};
		
		
		model.addAttribute("user", user);
		
		PageHelper.startPage(page, 5);
		PageInfo<Board> pageInfo = new PageInfo<>(svc.findWrite(idMac));
		System.out.println(svc.findWrite(idMac).toString());
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("page", page);
		
		return "thymeleaf/mac/User/myPage";

	}
	
//	회원 탈퇴
	@DeleteMapping("/removal")
	@ResponseBody
	public Map<String,Object> deleted(User user, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String idMac = user.getIdMac();
		boolean result = svc.deleted(idMac);
		map.put("result", result);
		
		if((String)session.getAttribute("idMac") == null){ //세션을 가져옴
			result = false;
			map.put("result", result);
			return map;
		};
		if(result==true) {
			session.removeAttribute("idMac");
		};
		
		return map;
	}
	
//  유저 업데이트폼
	@GetMapping("/updateForm")
	public String update(User user, Model model) {
		User _user = svc.getOne(user.getIdMac());
		model.addAttribute("user", _user);
		return "thymeleaf/mac/User/updateForm";
	}
	
//  유저 정보 수정
	@PutMapping("/modify")
	@ResponseBody
	public Map<String, Object> edit(User user, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		boolean result = svc.updated(user);
		map.put("result", result);
		return map;
	}
	
//	ID 중복체크
	@PostMapping("/idcheck")
	@ResponseBody
	public Map<String, Object> idcheck(@RequestParam("idMac")String idMac) {
		Map<String, Object> map = new HashMap<>();
		boolean result = svc.idcheck(idMac);
		map.put("result", result);
		map.put("id" , idMac);
		return map;
	}
	
//	email 인증 보내기
	@PostMapping("/email_authentication")
	@ResponseBody
	public Map<String, Object> emailcheck(@RequestParam("emailMac")String emailMac) {
		Map<String, Object> map = new HashMap<>();
		String random = svc.checkmail(emailMac);
		if(random!=null) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		map.put("code", random);
		map.put("emailMac", emailMac);
		return map;
	}
	
//	이메일 인증코드
	@PostMapping("/checkcode")
	@ResponseBody
	public Map<String, Object> checkcode(@RequestParam("code")String code) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", code);
		return map;
	}
	
//	닉네임
	@PostMapping("/nicknameCheck")
	@ResponseBody
	public Map<String, Object> nickCheck(@RequestParam("nick")String nick) {
		Map<String, Object> map = new HashMap<>();
		boolean result = svc.nickCheck(nick);
		map.put("result", result);
		return map;
	}
	
}
