package com.ty.zenxl.pojos;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

	@Size(min = 2, max = 20)
	private String username;
	@NotNull
	@Email
	private String email;
	@NotNull
	@Past
	private Date dateOfBirth;
	@NotBlank
	private String gender;
	@NotNull
	private String role;
	@Size(min = 4, max = 16)
	private String password;

}
