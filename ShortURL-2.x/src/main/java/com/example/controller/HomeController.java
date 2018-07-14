package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//Rest Contoller 와 Controller 어노테이션의 차이는  @responsebody의 추가 여부
//https://code.i-harness.com/ko/q/1812ad1

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "index";
	}
}
