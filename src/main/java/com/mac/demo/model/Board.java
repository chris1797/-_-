package com.mac.demo.model;


import org.springframework.stereotype.Component;

import lombok.*;

@Getter
@Setter
@Component
public class Board {

	private int numMac; //번호
	private String nickNameMac; //닉네임
	private String titleMac; //제목
	private String contentsMac; //내용
	private java.sql.Date wdateMac; //작성일
	private String typeMac; //속성(업종등)
	private String idMac; //유저아이디
	private int countMac;
	private String tableMac;
	private String categoryMac;

	
	
}