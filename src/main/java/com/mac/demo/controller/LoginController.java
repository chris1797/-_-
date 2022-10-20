package com.mac.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mac.demo.model.User;
import com.mac.demo.service.LoginService;


@RequestMapping("/login")
@RequiredArgsConstructor
@Controller
public class LoginController {
	
	@Autowired
	private LoginService svc;
	
//	로그인 페이지
	@GetMapping("/loginForm")
	public String login() {
		
		return "thymeleaf/mac/login/loginForm";
	}
	
//	세션에 id저장
	@PostMapping("/loginForm")
	public String login(@RequestParam("idMac")String idMac, @RequestParam("pwMac")String pwMac, User user, HttpSession session,Model model){
		
		//db에 사용자가 입력한 id와 pw가 있는지 확인
		String checkedId= svc.loginUser(idMac,pwMac);
		
//		checkdeId에 데이터가 있을시 세션에 id저장
		if(idMac.equals(checkedId)) {
		session.setAttribute("idMac", idMac);
		System.out.println(session.getAttribute("idMac").toString());
		model.addAttribute("idMac",session.getAttribute("idMac").toString());
		
		return "redirect:/home";
		}else if(checkedId==null) {
			model.addAttribute("msg","잘못된 아이디나 비밀번호 입니다");
			
			return "thymeleaf/mac/login/loginForm";
		}
		return "thymeleaf/mac/login/loginForm";	
	}
	
//	로그아웃메소드
	@GetMapping("/logout")
	@ResponseBody
	public Map<String,Object> logout(HttpSession session, Model model) 
	{	
		System.out.println(session.getAttribute("idMac"));
		
//		session.invalidate();
		session.removeAttribute("idMac");
		
		Map<String,Object> map = new HashMap<>();
		map.put("logout", true);
		return map;
	}
	
//	로그인 확인
	@GetMapping("/loginCheck")
	public boolean loginCheck(HttpSession session, Model model) 
	{
		String userId=(String) session.getAttribute("idMac");
		if(userId==null) {
			model.addAttribute("msg", "로그인 후 이용가능합니다");

			return false;
		}

		return true;
	}
	
//	아이디 찾기 폼(폼에서 이름,전화번호 기입후 alert창으로 아이디띄움)
	@GetMapping("/findId")
	public String findIdForm() {
		
		return "thymeleaf/mac/login/findId";
	}
	
//	아이디 찾기 메소드
	@PostMapping("/findId")
	public String findId(@RequestParam("nameMac")String nameMac, @RequestParam("emailMac")String emailMac, User user, HttpSession session,Model model){
		
		
		String foundId= svc.findId(nameMac,emailMac);
		
		if(foundId!=null){
		model.addAttribute("foundId",foundId);
		
		return "thymeleaf/mac/login/findId";
		}else{
			model.addAttribute("msg","이름이나 이메일이 잘못 입력되었습니다");
			
			return "thymeleaf/mac/login/findId";
		}
	}
	
//	비밀번호 찾기 폼(폼에서 아이디,이름,전화번호 기입후 alert창으로 비밀번호띄움)
	@GetMapping("/findPassword")
	public String findPasswordForm() {
		
		return "thymeleaf/mac/login/findPassword";
	}
	
//	비밀번호 찾기 메소드
	@PostMapping("/findPassword")
	public String findPassword(@RequestParam("idMac")String idMac,@RequestParam("nameMac")String nameMac, @RequestParam("emailMac")String emailMac, User user, HttpSession session,Model model){
		
		String msg = null;
		try {
			msg = svc.findPassword(idMac,nameMac,emailMac) ? "인증메일 전송 성공" : "인증메일 전송 실패";
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("msg",msg);
		
		return "thymeleaf/mac/login/findPassword";
	}

}
