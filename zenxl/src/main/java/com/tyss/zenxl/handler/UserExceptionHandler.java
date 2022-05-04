package com.tyss.zenxl.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tyss.zenxl.exception.OldPasswordException;
import com.tyss.zenxl.exception.ProperEmailException;
import com.tyss.zenxl.exception.UserAuthenticationFailedException;
import com.tyss.zenxl.response.UserResponse;
import com.tyss.zenxl.common.UserConstants;

@ControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(ProperEmailException.class)
	public ResponseEntity<UserResponse> handleIncorrectEmail(ProperEmailException renterPasswordException){
		UserResponse response= new UserResponse();
		response.setError(true);
		response.setMessage(UserConstants.EMAIL_ENTERED_FAILURE_MESSAGE);
		return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserAuthenticationFailedException.class)
	public ResponseEntity<UserResponse> handleUserAuthenticationFailure(UserAuthenticationFailedException userAuthenticationFailedException){
		UserResponse response= new UserResponse();
		response.setError(true);
		response.setMessage(UserConstants.USER_AUTHENTICATION_FAILURE_MESSAGE);
		return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(OldPasswordException.class)
	public ResponseEntity<UserResponse> handleUserOldPasswordFailure(OldPasswordException oldPasswordException){
		UserResponse response= new UserResponse();
		response.setError(true);
		response.setMessage(UserConstants.PASSWORD_CHANGE_FAILURE_MESSAGE);
		return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
	}

}
