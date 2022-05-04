package com.ty.zenxl.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

	private boolean isError;
	private String message;
	private JwtToken data;
	
	public LoginResponse(boolean isError, String message) {
		this.isError = isError;
		this.message = message;
	}
}
