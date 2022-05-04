package com.ty.zenxl.handler;

import static com.ty.zenxl.pojos.ZenxlConstantData.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ty.zenxl.exception.ChangePasswordException;
import com.ty.zenxl.exception.EmailInterruptionException;
import com.ty.zenxl.exception.LoginException;
import com.ty.zenxl.exception.SignUpException;
import com.ty.zenxl.exception.UserNotFoundException;
import com.ty.zenxl.exception.ZenxlErrorMessage;

@RestControllerAdvice
public class ZenxlExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = LoginException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ZenxlErrorMessage loginExceptionHandler(LoginException loginException) {
		return new ZenxlErrorMessage(IS_ERROR_TRUE, loginException.getMessage());
	}

	@ExceptionHandler(value = SignUpException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ZenxlErrorMessage signUpExceptionHandler(SignUpException signUpException) {
		return new ZenxlErrorMessage(IS_ERROR_TRUE, signUpException.getMessage());
	}

	@ExceptionHandler(value = UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ZenxlErrorMessage userNotFoundExceptionHandler(UserNotFoundException userNotFoundException) {
		return new ZenxlErrorMessage(IS_ERROR_TRUE, userNotFoundException.getMessage());
	}

	@ExceptionHandler(value = EmailInterruptionException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ZenxlErrorMessage userNotFoundExceptionHandler(EmailInterruptionException emailInterruptionException) {
		return new ZenxlErrorMessage(IS_ERROR_TRUE, emailInterruptionException.getMessage());
	}

	@ExceptionHandler(value = ChangePasswordException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ZenxlErrorMessage userNotFoundExceptionHandler(ChangePasswordException changePasswordException) {
		return new ZenxlErrorMessage(IS_ERROR_TRUE, changePasswordException.getMessage());
	}

	@Override
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> errorMap = new HashMap<>();
		Map<String, Object> validationErrorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> validationErrorMap.put(error.getField(), error.getDefaultMessage()));

		errorMap.put("isError", IS_ERROR_TRUE);
		errorMap.put("Validation Error", validationErrorMap);
		return ResponseEntity.badRequest().body(errorMap);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("isError", IS_ERROR_TRUE);
		errorMap.put("Error", ex.getMessage());

		return ResponseEntity.badRequest().body(errorMap);
	}
	
	
	
	
}
