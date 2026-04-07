package com.jdcompany.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

	@GetMapping("/")
	public Map<String, String> index() {
		return Map.of("status", "ok", "service", "jd-platform");
	}
}
