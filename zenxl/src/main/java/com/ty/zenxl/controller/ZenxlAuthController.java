package com.ty.zenxl.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ty.zenxl.pojos.ChangePassword;
import com.ty.zenxl.pojos.ChangePasswordResponse;
import com.ty.zenxl.pojos.ForgotPasswordResponse;
import com.ty.zenxl.pojos.LoginRequest;
import com.ty.zenxl.pojos.LoginResponse;
import com.ty.zenxl.pojos.SignUpRequest;
import com.ty.zenxl.pojos.SignUpResponse;
import com.ty.zenxl.service.ZenxlAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ZenxlAuthController {

	private final ZenxlAuthService zenxlAuthService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(zenxlAuthService.authenticateUser(request));
	}

	@PostMapping("/zenxl/signup")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SignUpResponse> addUser(@Valid @RequestBody SignUpRequest request) {
		return ResponseEntity.ok(zenxlAuthService.addUser(request));
	}

	@GetMapping("/zenxl/forgot-password")
	public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestParam() String email) {
		return ResponseEntity.ok(zenxlAuthService.forgotPassword(email));
	}

	@PutMapping("/zenxl/change-password")
	public ResponseEntity<ChangePasswordResponse> changePassword(@Valid @RequestBody ChangePassword request) {
		return ResponseEntity.ok(zenxlAuthService.changePassword(request));
	}

}
