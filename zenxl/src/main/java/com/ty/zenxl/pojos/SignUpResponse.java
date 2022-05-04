package com.ty.zenxl.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponse {

	private boolean isError;
	private String message;
	private SignnedUpUser data;
}

