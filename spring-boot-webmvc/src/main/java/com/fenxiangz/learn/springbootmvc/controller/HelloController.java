package com.fenxiangz.learn.springbootmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	@RequestMapping("/")
	public String index(Model view) {
		view.addAttribute("message", "aaaaaaa");
		return "index";
	}
}