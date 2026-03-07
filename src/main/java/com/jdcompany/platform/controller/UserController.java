package com.jdcompany.platform.controller;

import com.jdcompany.platform.dto.UserSignUpRequest;
import com.jdcompany.platform.dto.UserSignUpResponse;
import com.jdcompany.platform.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserSignUpResponse> signUp(@Valid @RequestBody UserSignUpRequest request) {
		Long userId = userService.signUp(request.email(), request.password());
		return ResponseEntity.status(HttpStatus.CREATED).body(new UserSignUpResponse(userId));
	}
}
