package com.fenxiangz.learn.springbootmvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestHelloController {

	@GetMapping(value = "/rest/hello-world")
	public Map helloworld() {
		HashMap<Object, Object> map = new HashMap<>();
		map.put("message", "Hello World!");
		return map;
	}
}