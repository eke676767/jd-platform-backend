package com.jdcompany.platform.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/home")
	public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
		String email = "로그인된 사용자";
		if (principal != null) {
			Map<String, Object> kakaoAccount = principal.getAttribute("kakao_account");
			if (kakaoAccount != null && kakaoAccount.get("email") != null) {
				email = (String) kakaoAccount.get("email");
			} else {
				Object id = principal.getAttribute("id");
				email = id != null ? "카카오 ID: " + id : email;
			}
		}
		model.addAttribute("userEmail", email);
		return "home";
	}
}
