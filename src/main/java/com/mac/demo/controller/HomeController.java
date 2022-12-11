package com.mac.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/home")
@RestController
public class HomeController {

	@GetMapping("")
	public ModelAndView home(HttpSession session) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/home/home");

		// session에 저장된 유저의 아이디 조회
		try {
			if(session.getAttribute("idMac")!=null) {
				String uid = session.getAttribute("idMac").toString();
				mav.addObject("idMac", uid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	
	@GetMapping("/dataSource")
	public ModelAndView dataSorce(HttpSession session) {
		ModelAndView mav = new ModelAndView("thymeleaf/mac/home/dataSource");

		if(session.getAttribute("idMac")!=null) {
			String uid = session.getAttribute("idMac").toString();
			mav.addObject("idMac",uid);
		}
		
		return mav;
	}

	
	@GetMapping("/siteIntroduction")
	public ModelAndView siteIntroduction(Model model,HttpSession session) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/home/siteIntroduction");

		if(session.getAttribute("idMac")!=null) {
			String uid = session.getAttribute("idMac").toString();
			mav.addObject("idMac",uid);
		}
		return mav;
	}
}