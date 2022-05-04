package com.ty.zenxl.pojos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePassword {

	@NotBlank
	@Email
	private String email;
	private int passcode;
	@NotBlank
	@Size(min = 4, max = 16)
	private String password;
	@NotBlank
	@Size(min = 4, max = 16)
	private String rePassword;
}
