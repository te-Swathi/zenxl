package com.tyss.zenxl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.zenxl.common.UserConstants;
import com.tyss.zenxl.dto.ForgotPassword;
import com.tyss.zenxl.entity.User;
import com.tyss.zenxl.exception.OldPasswordException;
import com.tyss.zenxl.exception.ProperEmailException;
import com.tyss.zenxl.exception.UserAuthenticationFailedException;
import com.tyss.zenxl.response.UserResponse;
import com.tyss.zenxl.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/register")
	public ResponseEntity<UserResponse> saveUser(@RequestBody User user) throws Exception {

		boolean inserted = userService.addUser(user) != null;
		UserResponse response = new UserResponse();
		if (inserted) {
			response.setError(false);
			response.setMessage("User Registered successfully");
			return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
		} else {
			response.setError(true);
			response.setMessage("User register failed");
			return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
		}

	}
	
	@PostMapping("/user/authenticate-user")
	public ResponseEntity<UserResponse> adminAuthenticate(@RequestBody User user) throws UserAuthenticationFailedException {

		UserResponse response = new UserResponse();
		
			User update = userService.userAuthentication(user);

			if (update != null) {
				response.setError(false);
				response.setMessage(UserConstants.USER_AUTHENTICATION_SUCCESS_MESSAGE);
				response.setUser(update);
			}
			return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
		
	}
	
	@PostMapping("/user/forgot-password")
	public ResponseEntity<UserResponse> forgotPassword(@RequestBody ForgotPassword password) throws OldPasswordException, ProperEmailException  {

		UserResponse response = new UserResponse();
		
			User update = userService.forgotPassword(password);

			if (update != null) {
				response.setError(false);
				response.setMessage(UserConstants.PASSWORD_CHANGE_SUCCESS_MESSAGE);
				response.setUser(update);
}
			
			return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
		
	}

}
